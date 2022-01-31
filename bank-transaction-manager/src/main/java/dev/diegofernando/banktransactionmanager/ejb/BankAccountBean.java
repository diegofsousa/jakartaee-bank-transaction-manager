package dev.diegofernando.banktransactionmanager.ejb;

import dev.diegofernando.banktransactionmanager.dto.MessageDTO;
import dev.diegofernando.banktransactionmanager.dto.bankaccount.BankAccountDepositDTO;
import dev.diegofernando.banktransactionmanager.dto.bankaccount.BankAccountOpeningDTO;
import dev.diegofernando.banktransactionmanager.dto.bankaccount.BankAccountOpeningResultDTO;
import dev.diegofernando.banktransactionmanager.dto.bankaccount.mapper.BankAccountDTOMapper;
import dev.diegofernando.banktransactionmanager.exception.BusinessException;
import dev.diegofernando.banktransactionmanager.model.BankAccount;
import dev.diegofernando.banktransactionmanager.model.BankStatement;
import dev.diegofernando.banktransactionmanager.model.Client;
import dev.diegofernando.banktransactionmanager.model.enums.Agency;
import dev.diegofernando.banktransactionmanager.model.enums.TypeChannelTransaction;
import dev.diegofernando.banktransactionmanager.model.enums.TypeMovementTransaction;
import dev.diegofernando.banktransactionmanager.util.BusinessMessageUtils;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Stateless
public class BankAccountBean {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private ClientBean clientBean;

    @Inject
    private BankStatementBean bankStatementBean;

    @Inject
    private BankAccountDTOMapper bankAccountDTOMapper;

    public static final BigDecimal ACCUMULATED_LIMIT_VALUE_OF_DEPOSITS_ON_THE_DAY = BigDecimal.valueOf(5_000L);

    public BankAccountOpeningResultDTO openBankAccount(BankAccountOpeningDTO bankAccountOpeningDTO) throws BusinessException {
        Client client = clientBean.findClientByExternalId(bankAccountOpeningDTO.getClientExternalId());
        Optional<BankAccount> userBankAccount = findBankAccountById(client.getId());

        if (userBankAccount.isPresent()){
            if (userBankAccount.get().getActive())
                throw new BusinessException(BusinessMessageUtils.ERROR_BANK_ACCOUNT_ALREADY_EXISTS);
            throw new BusinessException(BusinessMessageUtils.ERROR_BANK_ACCOUNT_ALREADY_EXISTS_BUT_INACTIVE);
        }

        long definedAccountNumber = findLastBankAccountByAgency(Agency.getAgencyEnumByNumber(bankAccountOpeningDTO.getAgency()).get())
                .map(bankAccount -> Long.parseLong(bankAccount.getNumber()) + 1L).orElse(1L);

        BankAccount bankAccount = new BankAccount(String.valueOf(definedAccountNumber), bankAccountOpeningDTO.getAgency(),
                client.typeAccount(), client);
        bankAccount.generateExternalId();

        em.persist(bankAccount);
        return bankAccountDTOMapper.openingObjToDTO(bankAccount);
    }

    public MessageDTO closeBankAccount(String externalId) throws BusinessException {
        BankAccount bankAccount = findBankAccountByExternalId(externalId);
        if (!bankAccount.getActive()){
            throw new BusinessException(BusinessMessageUtils.ERROR_BANK_ACCOUNT_ALREADY_INACTIVE);
        }

        if (bankAccount.getBalance().compareTo(BigDecimal.ZERO) > 0){
            throw new BusinessException(BusinessMessageUtils.ERROR_BANK_ACCOUNT_GREATER_THAN_ZERO);
        }
        bankAccount.setActive(false);
        return new MessageDTO("bankaccount.close.success", "Bank account closed successfully.");
    }

    public MessageDTO reopenBankAccount(String externalId) throws BusinessException {
        BankAccount bankAccount = findBankAccountByExternalId(externalId);
        if (bankAccount.getActive()){
            throw new BusinessException(BusinessMessageUtils.ERROR_BANK_ACCOUNT_ALREADY_ACTIVE);
        }

        bankAccount.setActive(true);
        return new MessageDTO("bankaccount.reopen.success", "Bank account reopening successfully.");
    }

    public MessageDTO deposit(BankAccountDepositDTO bankAccountDepositDTO) throws BusinessException {
        BankAccount bankAccount = findBankAccountByExternalId(bankAccountDepositDTO.getExternalId());
        if (!bankAccount.getActive()){
            throw new BusinessException(BusinessMessageUtils.ERROR_BANK_ACCOUNT_ALREADY_INACTIVE);
        }

        if(bankAccountDepositDTO.getValue().compareTo(BigDecimal.ZERO) == 0){
            throw new BusinessException(BusinessMessageUtils.ERROR_BANK_ACCOUNT_EQUAL_TO_ZERO);
        }

        BigDecimal sumValues = bankStatementBean.sumOfTheValueTransactionsTodayByType(bankAccount,
                TypeChannelTransaction.BANK_DEPOSIT,
                TypeMovementTransaction.CREDIT);

        if (sumValues.add(bankAccountDepositDTO.getValue()).compareTo(ACCUMULATED_LIMIT_VALUE_OF_DEPOSITS_ON_THE_DAY) >= 0){
            throw new BusinessException(BusinessMessageUtils.ERROR_BANK_ACCOUNT_DAY_LIMIT_REACHED);
        }

        bankStatementBean.createMovimentation(new BankStatement(bankAccountDepositDTO.getValue(), TypeChannelTransaction.BANK_DEPOSIT,
                TypeMovementTransaction.CREDIT, bankAccount));

        bankAccount.setBalance(bankAccount.getBalance().add(bankAccountDepositDTO.getValue()));

        return new MessageDTO("bankaccount.deposit.success", "Bank deposit made successfully.");
    }

    public Optional<BankAccount> findBankAccountById(Long id) {
        try {
            return Optional.of(em.find(BankAccount.class, id));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public BankAccount findBankAccountByExternalId(String externalId) throws BusinessException {
        try {
            TypedQuery<BankAccount> query = em.createNamedQuery("BankAccount.findByExternalId", BankAccount.class);
            query.setParameter("externalId", externalId);
            return query.getSingleResult();
        } catch (NoResultException e) {
            throw new BusinessException(BusinessMessageUtils.ERROR_NOT_FOUND);
        }
    }

    private Optional<BankAccount> findLastBankAccountByAgency(Agency agency){
        try {
            List<BankAccount> resultList = em.createNamedQuery("BankAccount.listByAgencyOpeningAtDesc", BankAccount.class)
                    .setParameter("agency", agency.getNumber())
                    .setMaxResults(1)
                    .getResultList();
            if (resultList.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(resultList.get(0));
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
