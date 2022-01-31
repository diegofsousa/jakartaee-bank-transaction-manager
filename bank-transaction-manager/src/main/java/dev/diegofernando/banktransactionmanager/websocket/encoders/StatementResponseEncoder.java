package dev.diegofernando.banktransactionmanager.websocket.encoders;

import com.google.gson.Gson;
import dev.diegofernando.banktransactionmanager.dto.financialmovement.FinancialMovementDTO;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class StatementResponseEncoder implements Encoder.Text<FinancialMovementDTO> {
    @Override
    public String encode(FinancialMovementDTO financialMovementDTO) throws EncodeException {
        return new Gson().toJson(financialMovementDTO);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
