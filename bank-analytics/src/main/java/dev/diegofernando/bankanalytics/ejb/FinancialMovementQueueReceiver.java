package dev.diegofernando.bankanalytics.ejb;

import com.google.gson.Gson;
import dev.diegofernando.bankanalytics.model.FinancialMovement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@MessageDriven(
        activationConfig = { @ActivationConfigProperty(
                propertyName = "destination", propertyValue = "java:/jms/queue/FinancialMovementQueue"),
                @ActivationConfigProperty(
                propertyName = "destinationType", propertyValue = "javax.jms.Queue")
        },
        mappedName = "java:/jms/queue/FinancialMovementQueue")
public class FinancialMovementQueueReceiver implements MessageListener {

    protected static final Logger logger = LoggerFactory.getLogger(FinancialMovementQueueReceiver.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;

        try {
            String textReceived = textMessage.getText();
            logger.info("Message received from BankTransactionManager: " + textReceived);
            em.persist(new Gson().fromJson(textReceived, FinancialMovement.class));
        } catch (JMSException e) {
            logger.error("Failed to persist received message.");
            e.printStackTrace();
        }

    }
}
