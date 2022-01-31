package dev.diegofernando.banktransactionmanager.ejb;

import dev.diegofernando.banktransactionmanager.dto.bankstatement.BankStatementMovimentationDTO;
import dev.diegofernando.banktransactionmanager.dto.bankstatement.mapper.BankStatementDTOMapper;
import dev.diegofernando.banktransactionmanager.dto.financialmovement.FinancialMovementDTO;
import dev.diegofernando.banktransactionmanager.dto.pixkeyregistration.PixKeyRegistrationDetailDTO;
import dev.diegofernando.banktransactionmanager.dto.pixkeyregistration.PixKeyRegistrationSimpleDTO;
import dev.diegofernando.banktransactionmanager.dto.pixkeyregistration.PixTransferDTO;
import dev.diegofernando.banktransactionmanager.dto.pixkeyregistration.mapper.PixKeyRegistrationDTOMapper;
import dev.diegofernando.banktransactionmanager.exception.BusinessException;
import dev.diegofernando.banktransactionmanager.external.ejb.FinancialMovementBean;
import dev.diegofernando.banktransactionmanager.model.*;
import dev.diegofernando.banktransactionmanager.model.enums.TypeAccount;
import dev.diegofernando.banktransactionmanager.model.enums.TypeChannelTransaction;
import dev.diegofernando.banktransactionmanager.model.enums.TypeMovementTransaction;
import dev.diegofernando.banktransactionmanager.model.enums.TypePixKey;
import dev.diegofernando.banktransactionmanager.util.BusinessMessageUtils;
import dev.diegofernando.banktransactionmanager.util.RandomStringGeneratorUtils;
import dev.diegofernando.banktransactionmanager.websocket.WsStatementsEndpoint;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Stateless
public class PixKeyResgistrationBean {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private BankAccountBean bankAccountBean;

    @Inject
    private BankStatementBean bankStatementBean;

    @Inject
    private FinancialMovementBean financialMovementBean;

    @Inject
    private WsStatementsEndpoint wsStatementsEndpoint;

    @Inject
    private PixKeyRegistrationDTOMapper pixKeyRegistrationDTOMapper;

    @Inject
    private BankStatementDTOMapper bankStatementDTOMapper;

    public static final String DEADLINE_FOR_PIX_TRANSACTIONS_OVER_THOUSAND_REAIS = "20:00:00";

    public static final long PIX_TRANSACTION_LIMIT_VALUE_AFTER_LIMIT_TIME = 1_000L;

    public PixKeyRegistrationDetailDTO registerPixKey(PixKeyRegistrationSimpleDTO pixKeyRegistrationDTO) throws BusinessException {
        BankAccount bankAccount = bankAccountBean.findBankAccountByExternalId(pixKeyRegistrationDTO.getAccountExternalId());

        if (!bankAccount.getActive())
            throw new BusinessException(BusinessMessageUtils.ERROR_BANK_ACCOUNT_ALREADY_INACTIVE);

        if (findPixKeyRegistrationByAccount(bankAccount).isPresent())
            throw new BusinessException(BusinessMessageUtils.ERROR_PIX_KEY_ALREADY_EXISTS);

        TypePixKey typePixKeySelected = TypePixKey.getTypePixEnumByValue(pixKeyRegistrationDTO.getKey());

        if (TypePixKey.DOCUMENT.equals(typePixKeySelected)){
            Client client = bankAccount.getClient();

            if (!checkValidKeyForDocument(pixKeyRegistrationDTO.getKey(), client, bankAccount.getTypeAccount()))
                throw new BusinessException(BusinessMessageUtils.ERROR_PIX_KEY_REGISTER_DOCUMENT_INVALID);

        } else if (TypePixKey.RANDOM_KEY.equals(typePixKeySelected))
            pixKeyRegistrationDTO.setKey(RandomStringGeneratorUtils.getRandomHashToPixKey());

        PixKeyRegistration pixKeyRegistration = new PixKeyRegistration(typePixKeySelected,
                pixKeyRegistrationDTO.getKey(), bankAccount);
        em.persist(pixKeyRegistration);

        return pixKeyRegistrationDTOMapper.objToDTO(pixKeyRegistration);

    }

    public BankStatementMovimentationDTO transferPix(PixTransferDTO pixTransferDTO) throws BusinessException {

        PixKeyRegistration pixKeyRegistrationOrigin =
                findPixKeyRegistrationByKey(pixTransferDTO.getKeyOrigin()).orElseThrow(() -> new BusinessException(BusinessMessageUtils.ERROR_PIX_ORIGIN_NOT_FOUND));

        PixKeyRegistration pixKeyRegistrationDestination =
                findPixKeyRegistrationByKey(pixTransferDTO.getKeyDestination()).orElseThrow(() -> new BusinessException(BusinessMessageUtils.ERROR_PIX_DESTINATION_NOT_FOUND));

        validationRulesForPixTransfer(pixTransferDTO, pixKeyRegistrationOrigin, pixKeyRegistrationDestination);

        pixKeyRegistrationOrigin.getBankAccount().setBalance(pixKeyRegistrationOrigin.getBankAccount().getBalance().subtract(pixTransferDTO.getValue()));

        BankStatement movimentation = bankStatementBean.createMovimentation(new BankStatement(pixTransferDTO.getValue(), TypeChannelTransaction.PIX
                , TypeMovementTransaction.DEBIT, pixKeyRegistrationOrigin.getBankAccount()));

        pixKeyRegistrationDestination.getBankAccount().setBalance(pixKeyRegistrationDestination.getBankAccount().getBalance().add(pixTransferDTO.getValue()));

        bankStatementBean.createMovimentation(new BankStatement(pixTransferDTO.getValue(), TypeChannelTransaction.PIX
                , TypeMovementTransaction.CREDIT, pixKeyRegistrationDestination.getBankAccount()));

        FinancialMovementDTO financialMovementDTO = new FinancialMovementDTO(pixKeyRegistrationOrigin.getBankAccount().getExternalId(),
                pixKeyRegistrationDestination.getBankAccount().getExternalId(), pixTransferDTO.getValue(),
                movimentation.getExternalId());

        financialMovementBean.send(financialMovementDTO);

        wsStatementsEndpoint.broadcastMessage(financialMovementDTO);

        return bankStatementDTOMapper.objToDTO(movimentation);

    }

    private Optional<PixKeyRegistration> findPixKeyRegistrationByAccount(BankAccount bankAccount) {
        try {
            TypedQuery<PixKeyRegistration> query = em.createNamedQuery("PixKeyRegistration.findByBankAccount", PixKeyRegistration.class);
            query.setParameter("bankAccount", bankAccount);
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    private Optional<PixKeyRegistration> findPixKeyRegistrationByKey(String pixKey) {
        try {
            TypedQuery<PixKeyRegistration> query = em.createNamedQuery("PixKeyRegistration.findByBankByKey", PixKeyRegistration.class);
            query.setParameter("pixKey", pixKey);
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    private boolean checkValidKeyForDocument(String pixKeyDto, Client client, TypeAccount typeAccount){
        if (TypeAccount.NATURAL_PERSON.equals(typeAccount))
            return pixKeyDto.equals(((NaturalPerson) client).getCpf());
        else return pixKeyDto.equals(((LegalEntity) client).getCnpj());
    }

    private void validationRulesForPixTransfer(PixTransferDTO pixTransferDTO,
                                               PixKeyRegistration pixKeyRegistrationOrigin,
                                               PixKeyRegistration pixKeyRegistrationDestination) throws BusinessException {

        if (pixTransferDTO.getKeyOrigin().equals(pixTransferDTO.getKeyDestination()))
            throw new BusinessException(BusinessMessageUtils.ERROR_PIX_KEY_ORIGIN_DESTINATION_BE_THE_SAME);

        if (!pixKeyRegistrationOrigin.getBankAccount().getActive())
            throw new BusinessException(BusinessMessageUtils.ERROR_PIX_KEY_REGISTER_DOCUMENT_INVALID);

        if (pixKeyRegistrationOrigin.getBankAccount().getBalance().compareTo(pixTransferDTO.getValue()) < 0)
            throw new BusinessException(BusinessMessageUtils.ERROR_BANK_ACCOUNT_INSUFFICIENT_BALANCE);

        if (!pixKeyRegistrationDestination.getBankAccount().getActive())
            throw new BusinessException(BusinessMessageUtils.ERROR_PIX_DESTINATION_NOT_FOUND);

        try {
            if (new Date().getHours() >= getTimeOfString(DEADLINE_FOR_PIX_TRANSACTIONS_OVER_THOUSAND_REAIS).getHours()
                    && pixTransferDTO.getValue().compareTo(BigDecimal.valueOf(PIX_TRANSACTION_LIMIT_VALUE_AFTER_LIMIT_TIME)) > 0)
                throw new BusinessException(BusinessMessageUtils.ERROR_BANK_ACCOUNT_HOUR_LIMIT_REACHED);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private Date getTimeOfString(String time) throws ParseException {
        Date dateTime = new SimpleDateFormat("HH:mm:ss").parse(time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }


}
