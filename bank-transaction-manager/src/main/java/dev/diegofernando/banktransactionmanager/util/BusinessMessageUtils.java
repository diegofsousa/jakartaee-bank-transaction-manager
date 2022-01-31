package dev.diegofernando.banktransactionmanager.util;

import javax.ws.rs.core.Response;

public enum BusinessMessageUtils {
    ERROR_NUM_SIZE_PAGE(Response.Status.BAD_REQUEST, "validation.page.size.invalid",
            "The number of elements for the page must be greater than zero."),
    ERROR_NUM_PAGE(Response.Status.BAD_REQUEST, "validation.page.invalid",
            "The number of the page must be greater than zero."),
    ERROR_INVALID_INPUT_REGISTER(Response.Status.BAD_REQUEST,"bad.request", "Invalid Input. To register an individual, fill " +
            "in the fields " +
            "'name', 'cpf' and 'phoneNumber'. To register a legal entity, fill in 'socialName', 'cnpj' and " +
            "'phoneNumber'."),
    ERROR_NOT_FOUND(Response.Status.NOT_FOUND, "not.found", "No results found."),
    ERROR_BANK_ACCOUNT_ALREADY_CREATED(Response.Status.BAD_REQUEST, "bankaccount.already.created", "A bank account " +
            "has already been created for this customer. Try migrating account type."),
    ERROR_BANK_AGENCY_NOT_FOUND(Response.Status.NOT_FOUND, "bankaccount.agency.not.found", "Banking agency does not " +
            "exist."),
    ERROR_BANK_ACCOUNT_ALREADY_EXISTS(Response.Status.BAD_REQUEST, "bankaccount.already.exists", "This client already" +
            " " +
            "has an active bank account."),
    ERROR_BANK_ACCOUNT_ALREADY_EXISTS_BUT_INACTIVE(Response.Status.BAD_REQUEST, "bankaccount.already.exists.inactive",
            "This client has a deactivated bank account. Try the account reopening option."),
    ERROR_BANK_ACCOUNT_ALREADY_INACTIVE(Response.Status.BAD_REQUEST, "bankaccount.inactive",
            "This bank account is deactivated."),
    ERROR_BANK_ACCOUNT_ALREADY_ACTIVE(Response.Status.BAD_REQUEST, "bankaccount.active",
            "This bank account is activated."),
    ERROR_BANK_ACCOUNT_GREATER_THAN_ZERO(Response.Status.BAD_REQUEST, "bankaccount.balance.greaterthanzero",
            "To be closed, the account must have a zero balance."),
    ERROR_BANK_ACCOUNT_EQUAL_TO_ZERO(Response.Status.BAD_REQUEST, "bankaccount.balance.equaltozero",
            "Deposit amount must be greater than zero."),
    ERROR_BANK_ACCOUNT_DAY_LIMIT_REACHED(Response.Status.BAD_REQUEST, "bankaccount.maximum.day.limit",
            "The deposit limit amount per day has been reached."),
    ERROR_PIX_KEY_REGISTER_DOCUMENT_INVALID(Response.Status.BAD_REQUEST, "pix.key.invalid.document",
            "Error registering Pix key. Document (CPF or CNPJ) is not linked to the customer."),
    ERROR_PIX_KEY_ORIGIN_DESTINATION_BE_THE_SAME(Response.Status.BAD_REQUEST, "pix.key.invalid.bethesame",
            "Origin and destination pix key cannot be the same."),
    ERROR_PIX_KEY_ALREADY_EXISTS(Response.Status.BAD_REQUEST, "pix.key.already.exists",
            "This client already has a registered PIX key."),
    ERROR_PIX_ORIGIN_NOT_FOUND(Response.Status.NOT_FOUND, "pix.key.origin.not.found",
            "Origin pix key not found."),
    ERROR_PIX_DESTINATION_NOT_FOUND(Response.Status.NOT_FOUND, "pix.key.destination.not.found",
            "Destination pix key not found."),
    ERROR_PIX_ORIGIN_BANK_ACCOUNT_NOT_ACTIVE(Response.Status.BAD_REQUEST, "pix.key.origin.bankaccount.not.active",
            "Origin bank account not active."),
    ERROR_BANK_ACCOUNT_HOUR_LIMIT_REACHED(Response.Status.BAD_REQUEST, "pix.maximum.hour.limit",
            "The pix transfer limit amount per hour has been reached."),
    ERROR_BANK_ACCOUNT_INSUFFICIENT_BALANCE(Response.Status.BAD_REQUEST, "pix.account.insufficient.balance",
            "Origin account with insufficient balance.");

    private Response.Status httpStatus;
    private String key;
    private String message;

    BusinessMessageUtils(Response.Status httpStatus, String key, String message) {
        this.httpStatus = httpStatus;
        this.key = key;
        this.message = message;
    }

    public Response.Status getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(Response.Status httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
