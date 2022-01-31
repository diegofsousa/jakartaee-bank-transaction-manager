package dev.diegofernando.banktransactionmanager.exception.mapper;

import dev.diegofernando.banktransactionmanager.dto.ErrorDTO;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    public static final String ERROR_KEY = "bad.request";

    @Override
    public Response toResponse(ConstraintViolationException e) {
        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorDTO(ERROR_KEY, prepareMessage(e))).build();
    }

    private HashMap<String, String> prepareMessage(ConstraintViolationException exception) {
        HashMap<String, String> response = new HashMap<>();
        for (ConstraintViolation<?> cv : exception.getConstraintViolations()) {
            response.put(cv.getPropertyPath().toString(), cv.getMessage());
        }
        return response;
    }
}
