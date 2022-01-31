package dev.diegofernando.banktransactionmanager.ejb;

import dev.diegofernando.banktransactionmanager.model.LegalEntity;
import dev.diegofernando.banktransactionmanager.model.NaturalPerson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton
@Startup
public class StartupBean {

    protected static final Logger logger = LoggerFactory.getLogger(StartupBean.class);

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    public void setup() {

        logger.info("Creating customer records for testing purposes...");

        NaturalPerson naturalPerson = new NaturalPerson("Person 1", "11111111111");
        naturalPerson.setPhoneNumber("89911111111");
        naturalPerson.generateExternalId();
        em.persist(naturalPerson);

        logger.info("Person 1 Created");

        LegalEntity legalEntity = new LegalEntity("Entity 2", "22222222222222");
        legalEntity.setPhoneNumber("8992222222");
        legalEntity.generateExternalId();
        em.persist(legalEntity);

        logger.info("Entity 2 Created");
    }
}
