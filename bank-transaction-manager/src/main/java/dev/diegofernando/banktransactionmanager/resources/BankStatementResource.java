package dev.diegofernando.banktransactionmanager.resources;

import dev.diegofernando.banktransactionmanager.dto.ListDTO;
import dev.diegofernando.banktransactionmanager.dto.MessageDTO;
import dev.diegofernando.banktransactionmanager.dto.bankstatement.BankStatementMovimentationDTO;
import dev.diegofernando.banktransactionmanager.ejb.BankStatementBean;
import dev.diegofernando.banktransactionmanager.exception.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/statements")
@Api("Statements management endpoints")
public class BankStatementResource extends AbstractResource{

    @Inject
    private BankStatementBean bankStatementBean;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{accountExternalId}")
    @ApiOperation(value = "List an account's bank statement")
    @ApiResponses({
            @ApiResponse(code=200, message="List of extract transactions.", response = ListDTO.class),
            @ApiResponse(code=404, message="No results found."),
            @ApiResponse(code=400, message="The number of elements for the page must be greater than zero. | The number of the page must be greater than zero."),
    })
    public Response list(@DefaultValue("10") @QueryParam("size") int size,
                         @DefaultValue("1") @QueryParam("page") int page,
                         @PathParam("accountExternalId") String accountExternalId) throws BusinessException {
        validateSizeAndPage(size, page);
        return Response.ok().entity(bankStatementBean.listByAccountExternalId(accountExternalId, size, page)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/detail/{externalId}")
    @ApiOperation(value = "Detail of a transaction")
    @ApiResponses({
            @ApiResponse(code=200, message="Transaction detail.", response = BankStatementMovimentationDTO.class),
            @ApiResponse(code=404, message="No results found.")
    })
    public Response detailStatement(@PathParam("externalId") String externalId) throws BusinessException {
        return Response.ok().entity(bankStatementBean.detailStatement(externalId)).build();
    }

}
