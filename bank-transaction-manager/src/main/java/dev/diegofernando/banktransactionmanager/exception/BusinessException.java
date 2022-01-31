package dev.diegofernando.banktransactionmanager.exception;

import dev.diegofernando.banktransactionmanager.util.BusinessMessageUtils;

import javax.ws.rs.core.Response;

public class BusinessException extends Exception{
    private String key;
    private Response.Status httpStatus;

    public BusinessException(String key, String message) {
        super(message);
        this.setKey(key);
    }

    public BusinessException(String key, String message, Throwable cause) {
        super(message, cause);
        this.setKey(key);
    }

    public BusinessException(BusinessMessageUtils messageUtil) {
        super(messageUtil.getMessage());
        this.setKey(messageUtil.getKey());
        this.setHttpStatus(messageUtil.getHttpStatus());
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Response.Status getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(Response.Status httpStatus) {
        this.httpStatus = httpStatus;
    }
}
