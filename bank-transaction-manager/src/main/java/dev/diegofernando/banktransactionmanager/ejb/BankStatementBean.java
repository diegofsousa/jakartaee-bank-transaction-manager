package dev.diegofernando.banktransactionmanager.ejb;

import dev.diegofernando.banktransactionmanager.dto.ListDTO;
import dev.diegofernando.banktransactionmanager.dto.bankstatement.BankStatementMovimentationDTO;
import dev.diegofernando.banktransactionmanager.dto.bankstatement.mapper.BankStatementDTOMapper;
import dev.diegofernando.banktransactionmanager.exception.BusinessException;
import dev.diegofernando.banktransactionmanager.model.BankAccount;
import dev.diegofernando.banktransactionmanager.model.BankStatement;
import dev.diegofernando.banktransactionmanager.model.BankStatement_;
import dev.diegofernando.banktransactionmanager.model.enums.TypeChannelTransaction;
import dev.diegofernando.banktransactionmanager.model.enums.TypeMovementTransaction;
import dev.diegofernando.banktransactionmanager.util.BusinessMessageUtils;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class BankStatementBean {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private BankAccountBean bankAccountBean;

    @Inject
    private BankStatementDTOMapper bankStatementDTOMapper;

    public ListDTO<BankStatementMovimentationDTO> listByAccountExternalId(String accountExternalId, int size, int page) throws BusinessException {
        BankAccount bankAccount = bankAccountBean.findBankAccountByExternalId(accountExternalId);

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<BankStatement> q = cb.createQuery(BankStatement.class);

        Root<BankStatement> bankStatementRoot = q.from(BankStatement.class);
        q.where(cb.equal(bankStatementRoot.get(BankStatement_.BANK_ACCOUNT), bankAccount));
        q.orderBy(cb.asc(bankStatementRoot.get(BankStatement_.CREATED_AT)));

        List<BankStatement> resultList = em.createQuery(q)
                .setFirstResult(size * (page - 1))
                .setMaxResults(size).getResultList();

        List<BankStatementMovimentationDTO> resultListCollect =
                resultList.stream().map(bankStatement -> bankStatementDTOMapper.objToDTO(bankStatement))
                .collect(Collectors.toList());

        return new ListDTO<>(resultListCollect, size, page);
    }

    public BankStatementMovimentationDTO detailStatement(String externalId) throws BusinessException {
        try {
            TypedQuery<BankStatement> query = em.createNamedQuery("BankStatement.findByExternalId", BankStatement.class);
            query.setParameter("externalId", externalId);
            return bankStatementDTOMapper.objToDTO(query.getSingleResult());
        } catch (NoResultException e) {
            throw new BusinessException(BusinessMessageUtils.ERROR_NOT_FOUND);
        }
    }

    public BankStatement createMovimentation(BankStatement bankStatement){
        bankStatement.generateExternalId();
        em.persist(bankStatement);
        return bankStatement;
    }

    public BigDecimal sumOfTheValueTransactionsTodayByType(BankAccount bankAccount,
                                                           TypeChannelTransaction typeChannelTransaction,
                                                           TypeMovementTransaction typeMovementTransaction) throws BusinessException {
        try {
            TypedQuery<BigDecimal> query = em.createNamedQuery("BankStatement.sumOfTheValueTransactionsTodayByType", BigDecimal.class);
            query.setParameter("bankAccount", bankAccount);
            query.setParameter("typeChannelTransaction", typeChannelTransaction);
            query.setParameter("typeMovementTransaction", typeMovementTransaction);
            return query.getSingleResult() != null ? query.getSingleResult() : BigDecimal.ZERO;
        } catch (NoResultException e) {
            throw new BusinessException(BusinessMessageUtils.ERROR_NOT_FOUND);
        }

    }


}
