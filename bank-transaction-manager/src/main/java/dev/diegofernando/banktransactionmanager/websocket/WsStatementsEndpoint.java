package dev.diegofernando.banktransactionmanager.websocket;

import dev.diegofernando.banktransactionmanager.dto.financialmovement.FinancialMovementDTO;
import dev.diegofernando.banktransactionmanager.websocket.encoders.StatementResponseEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@ServerEndpoint(value = "/realtime/statements", encoders = {StatementResponseEncoder.class})
public class WsStatementsEndpoint {

    protected static final Logger logger = LoggerFactory.getLogger(WsStatementsEndpoint.class);

    protected static Queue<Session> sessions = new ConcurrentLinkedQueue<>();

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        logger.info("Connection open!");
        sessions.add(session);
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        logger.info("Connection closed!");
        sessions.remove(session);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        logger.info("Error!");
        throwable.printStackTrace();
    }

    public void broadcastMessage(FinancialMovementDTO financialMovementDTO){
        for(Session session: sessions) {
            try {
                session.getBasicRemote().sendObject(financialMovementDTO);
            } catch (IOException | EncodeException e) {
                e.printStackTrace();
            }
        }
    }

}
