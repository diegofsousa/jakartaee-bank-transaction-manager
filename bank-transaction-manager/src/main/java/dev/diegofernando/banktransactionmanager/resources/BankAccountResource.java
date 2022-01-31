package dev.diegofernando.banktransactionmanager.resources;

import dev.diegofernando.banktransactionmanager.dto.MessageDTO;
import dev.diegofernando.banktransactionmanager.dto.bankaccount.BankAccountDepositDTO;
import dev.diegofernando.banktransactionmanager.dto.bankaccount.BankAccountOpeningDTO;
import dev.diegofernando.banktransactionmanager.dto.bankaccount.BankAccountOpeningResultDTO;
import dev.diegofernando.banktransactionmanager.ejb.BankAccountBean;
import dev.diegofernando.banktransactionmanager.exception.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/accounts")
@Api("Bank account management endpoints")
public class BankAccountResource extends AbstractResource {

    @Inject
    private BankAccountBean bankAccountBean;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Opening a bank account for a client")
    @ApiResponses({
            @ApiResponse(code=201, message="Bank account created", response = BankAccountOpeningDTO.class),
            @ApiResponse(code=404, message="No results found."),
            @ApiResponse(code=400, message="This client already has an active bank account. | This client has a deactivated bank account." +
                    " Try the account reopening option."),
    })
    public Response openBankAccount(@Valid BankAccountOpeningDTO bankAccountOpeningDTO, @Context UriInfo uriInfo) throws BusinessException {
        BankAccountOpeningResultDTO register = bankAccountBean.openBankAccount(bankAccountOpeningDTO);
        return Response.created(buildURI(uriInfo, register.getExternalId())).entity(register).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{externalId}")
    @ApiOperation(value = "Bank account closing")
    @ApiResponses({
            @ApiResponse(code=200, message="Bank account closed successfully.", response = MessageDTO.class),
            @ApiResponse(code=404, message="No results found."),
            @ApiResponse(code=400, message="This bank account is deactivated. | To be closed, the account must have a" +
                    " zero balance. "),
    })
    public Response closeBankAccount(@PathParam("externalId") String externalId) throws BusinessException {
        return Response.ok().entity(bankAccountBean.closeBankAccount(externalId)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{externalId}/reopen")
    @ApiOperation(value = "Reopen bank account")
    @ApiResponses({
            @ApiResponse(code=200, message="Bank account reopening successfully.", response = MessageDTO.class),
            @ApiResponse(code=404, message="No results found."),
            @ApiResponse(code=400, message="This bank account is activated."),
    })
    public Response reopenBankAccount(@PathParam("externalId") String externalId) throws BusinessException {
        return Response.ok().entity(bankAccountBean.reopenBankAccount(externalId)).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Make a bank deposit")
    @ApiResponses({
            @ApiResponse(code=200, message="Bank deposit made successfully.", response = MessageDTO.class),
            @ApiResponse(code=404, message="No results found."),
            @ApiResponse(code=400, message="This bank account is deactivated. | Deposit amount must be greater than " +
                    "zero. | The deposit limit amount per day has been reached."),
    })
    public Response deposit(@Valid BankAccountDepositDTO bankAccountDepositDTO) throws BusinessException {
        return Response.ok().entity(bankAccountBean.deposit(bankAccountDepositDTO)).build();
    }

}
