package dev.diegofernando.banktransactionmanager.exception.mapper;

import dev.diegofernando.banktransactionmanager.dto.MessageDTO;
import dev.diegofernando.banktransactionmanager.exception.BusinessException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BusinessExceptionMapper implements ExceptionMapper<BusinessException> {

    @Override
    public Response toResponse(BusinessException e) {
        return Response.status(e.getHttpStatus()).entity(new MessageDTO(e.getKey(), e.getMessage())).build();
    }
}
