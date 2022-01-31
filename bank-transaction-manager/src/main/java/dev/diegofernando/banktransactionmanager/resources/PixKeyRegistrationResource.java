package dev.diegofernando.banktransactionmanager.resources;

import dev.diegofernando.banktransactionmanager.dto.bankstatement.BankStatementMovimentationDTO;
import dev.diegofernando.banktransactionmanager.dto.pixkeyregistration.PixKeyRegistrationDetailDTO;
import dev.diegofernando.banktransactionmanager.dto.pixkeyregistration.PixKeyRegistrationSimpleDTO;
import dev.diegofernando.banktransactionmanager.dto.pixkeyregistration.PixTransferDTO;
import dev.diegofernando.banktransactionmanager.ejb.PixKeyResgistrationBean;
import dev.diegofernando.banktransactionmanager.exception.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/pix")
@Api("Pix transfer endpoints")
public class PixKeyRegistrationResource extends AbstractResource {

    @Inject
    private PixKeyResgistrationBean pixKeyResgistrationBean;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Pix key registration for a bank account")
    @ApiResponses({
            @ApiResponse(code=201, message="Registered Pix Key", response = PixKeyRegistrationDetailDTO.class),
            @ApiResponse(code=404, message="No results found."),
            @ApiResponse(code=400, message="This bank account is deactivated. | This client already has a registered " +
                    "PIX key. | Error registering Pix key. Document (CPF or CNPJ) is not linked to the customer."),
    })
    public Response registerPixKey(@Valid PixKeyRegistrationSimpleDTO pixKeyRegistrationSimpleDTO, @Context UriInfo uriInfo) throws BusinessException {
        PixKeyRegistrationDetailDTO register = pixKeyResgistrationBean.registerPixKey(pixKeyRegistrationSimpleDTO);
        return Response.created(buildURI(uriInfo, pixKeyRegistrationSimpleDTO.getAccountExternalId())).entity(register).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/transfer")
    @ApiOperation(value = "Pix transfer between two clients")
    @ApiResponses({
            @ApiResponse(code=200, message="Pix transfer successful", response = BankStatementMovimentationDTO.class),
            @ApiResponse(code=404, message="No results found."),
            @ApiResponse(code=400, message="Origin pix key not found. | Destination pix key not found. | Origin and " +
                    "destination pix key cannot be the same. | Error registering Pix key. Document (CPF or CNPJ) is " +
                    "not linked to the customer. | Origin account with insufficient balance. | Destination pix key " +
                    "not found. | The pix transfer limit amount per hour has been reached."),
    })
    public Response transferPix(@Valid PixTransferDTO pixTransferDTO) throws BusinessException {
        return Response.ok().entity(pixKeyResgistrationBean.transferPix(pixTransferDTO)).build();
    }
}
