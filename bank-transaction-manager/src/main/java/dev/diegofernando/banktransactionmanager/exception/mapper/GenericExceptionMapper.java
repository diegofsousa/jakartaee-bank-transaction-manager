package dev.diegofernando.banktransactionmanager.exception.mapper;

import dev.diegofernando.banktransactionmanager.dto.MessageDTO;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Exception> {

    public static final String ERROR_KEY = "internal.error";
    public static final String ERROR_MESSAGE = "Internal server error. See the logs.";

    @Override
    public Response toResponse(Exception e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new MessageDTO(ERROR_KEY, ERROR_MESSAGE)).build();
    }
}
