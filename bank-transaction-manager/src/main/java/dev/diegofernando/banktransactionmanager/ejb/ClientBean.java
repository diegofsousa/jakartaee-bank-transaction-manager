package dev.diegofernando.banktransactionmanager.ejb;

import dev.diegofernando.banktransactionmanager.dto.MessageDTO;
import dev.diegofernando.banktransactionmanager.dto.client.ClientDetailDTO;
import dev.diegofernando.banktransactionmanager.dto.client.ClientSimpleDTO;
import dev.diegofernando.banktransactionmanager.dto.client.mapper.ClientDTOMapper;
import dev.diegofernando.banktransactionmanager.exception.BusinessException;
import dev.diegofernando.banktransactionmanager.model.Client;
import dev.diegofernando.banktransactionmanager.model.LegalEntity;
import dev.diegofernando.banktransactionmanager.model.NaturalPerson;
import dev.diegofernando.banktransactionmanager.util.BusinessMessageUtils;
import dev.diegofernando.banktransactionmanager.util.StringUtils;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class ClientBean {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private BankAccountBean bankAccountBean;

    @Inject
    private ClientDTOMapper clientDTOMapper;

    public List<ClientSimpleDTO> list(){
        List<Client> resultList = em.createNamedQuery("Client.listAllByCreatedAtAsc", Client.class).getResultList();
        return resultList.stream().map(clientDTOMapper::registerObjToDTO).collect(Collectors.toList());
    }

    public ClientSimpleDTO register(ClientSimpleDTO clientSimpleDTO) throws BusinessException {
        if (isNaturalPerson(clientSimpleDTO)){
            NaturalPerson naturalPerson = new NaturalPerson(clientSimpleDTO.getName(), clientSimpleDTO.getCpf());
            naturalPerson.setPhoneNumber(clientSimpleDTO.getPhoneNumber());
            naturalPerson.generateExternalId();
            em.persist(naturalPerson);
            return clientDTOMapper.registerObjToDTO(naturalPerson);
        } else if (isLegalEntity(clientSimpleDTO)){
            LegalEntity legalEntity = new LegalEntity(clientSimpleDTO.getSocialName(), clientSimpleDTO.getCnpj());
            legalEntity.setPhoneNumber(clientSimpleDTO.getPhoneNumber());
            legalEntity.generateExternalId();
            em.persist(legalEntity);
            return clientDTOMapper.registerObjToDTO(legalEntity);
        } else {
            throw new BusinessException(BusinessMessageUtils.ERROR_INVALID_INPUT_REGISTER);
        }
    }

    public ClientDetailDTO detail(String externalId) throws BusinessException {
        return clientDTOMapper.detailObjToDTO(findClientByExternalId(externalId));
    }

    public MessageDTO update(String externalId, ClientSimpleDTO clientSimpleDTO) throws BusinessException {
        Client client = findClientByExternalId(externalId);
        bankAccountBean.findBankAccountById(client.getId()).orElseThrow(() -> new BusinessException(BusinessMessageUtils.ERROR_BANK_ACCOUNT_ALREADY_CREATED));

        if (isNaturalPerson(clientSimpleDTO)){
            if (client instanceof LegalEntity) updateToNaturalPerson(client, clientSimpleDTO);
            else updateNaturalPerson(client, clientSimpleDTO);
        } else if (isLegalEntity(clientSimpleDTO)){
            if (client instanceof NaturalPerson) updateToLegalEntity(client, clientSimpleDTO);
            else updateLegalEntity(client, clientSimpleDTO);
        } else {
            throw new BusinessException(BusinessMessageUtils.ERROR_INVALID_INPUT_REGISTER);
        }

        client.setUpdatedAt(new Date());

        return new MessageDTO("update.client.success", "Customer data has been updated.");
    }

    public Client findClientByExternalId(String externalId) throws BusinessException {
        try {
            TypedQuery<Client> query = em.createNamedQuery("Client.findByExternalId", Client.class);
            query.setParameter("externalId", externalId);
            return query.getSingleResult();
        } catch (NoResultException e) {
            throw new BusinessException(BusinessMessageUtils.ERROR_NOT_FOUND);
        }
    }

    private void updateToLegalEntity(Client client, ClientSimpleDTO clientSimpleDTO) {
        em.createNamedQuery("Client.deleteNaturalPersonById")
                .setParameter(1, client.getId())
                .executeUpdate();

        em.createNamedQuery("Client.insertLegalEntityWithClientAlreadyRegistered")
                .setParameter(1, clientSimpleDTO.getCnpj())
                .setParameter(2, clientSimpleDTO.getSocialName())
                .setParameter(3, client.getId())
                .executeUpdate();
    }

    private void updateLegalEntity(Client client, ClientSimpleDTO clientSimpleDTO){
        LegalEntity legalEntity = (LegalEntity) client;
        legalEntity.setSocialName(clientSimpleDTO.getSocialName());
        legalEntity.setCnpj(clientSimpleDTO.getCnpj());
        legalEntity.setPhoneNumber(clientSimpleDTO.getPhoneNumber());
    }

    private void updateToNaturalPerson(Client client, ClientSimpleDTO clientSimpleDTO) {
        em.createNamedQuery("Client.deleteLegalEntityById")
                .setParameter(1, client.getId())
                .executeUpdate();

        em.createNamedQuery("Client.insertNaturalPersonWithClientAlreadyRegistered")
                .setParameter(1, clientSimpleDTO.getCpf())
                .setParameter(2, clientSimpleDTO.getName())
                .setParameter(3, client.getId())
                .executeUpdate();
    }

    private void updateNaturalPerson(Client client, ClientSimpleDTO clientSimpleDTO){
        NaturalPerson naturalPerson = (NaturalPerson) client;
        naturalPerson.setName(clientSimpleDTO.getName());
        naturalPerson.setCpf(clientSimpleDTO.getCpf());
        naturalPerson.setPhoneNumber(clientSimpleDTO.getPhoneNumber());
    }

    public Boolean isNaturalPerson(ClientSimpleDTO clientSimpleDTO){
        return StringUtils.validateNullOrEmptyInList(clientSimpleDTO.getName(), clientSimpleDTO.getCpf());
    }

    public Boolean isLegalEntity(ClientSimpleDTO clientSimpleDTO){
        return StringUtils.validateNullOrEmptyInList(clientSimpleDTO.getSocialName(), clientSimpleDTO.getCnpj());
    }

}
