package dev.diegofernando.banktransactionmanager.external.ejb;

import com.google.gson.Gson;
import dev.diegofernando.banktransactionmanager.dto.financialmovement.FinancialMovementDTO;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import java.io.Serializable;

@Stateless
public class FinancialMovementBean implements Serializable {

    @Resource(mappedName = "java:/ConnectionFactory")
    private ConnectionFactory connFactory;

    @Resource(mappedName = "java:/jms/queue/FinancialMovementQueue")
    private Queue queue;

    public void send(FinancialMovementDTO financialMovementDTO) {
        JMSContext context = connFactory.createContext();
        JMSProducer producer = context.createProducer();
        producer.send(queue, new Gson().toJson(financialMovementDTO));
    }
}
