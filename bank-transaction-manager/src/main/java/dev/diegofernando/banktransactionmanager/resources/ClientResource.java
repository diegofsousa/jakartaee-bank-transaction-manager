package dev.diegofernando.banktransactionmanager.resources;

import dev.diegofernando.banktransactionmanager.dto.MessageDTO;
import dev.diegofernando.banktransactionmanager.dto.client.ClientDetailDTO;
import dev.diegofernando.banktransactionmanager.dto.client.ClientSimpleDTO;
import dev.diegofernando.banktransactionmanager.ejb.ClientBean;
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

@Path("/clients")
@Api("Application client management endpoints")
public class ClientResource extends AbstractResource {

    @Inject
    private ClientBean clientBean;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "List all clients")
    @ApiResponses({
            @ApiResponse(code=200, message="Client list", response = ClientSimpleDTO.class, responseContainer = "List")
    })
    public Response listAll(){
        return Response.ok().entity(clientBean.list()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Register a new client")
    @ApiResponses({
            @ApiResponse(code=200, message="Registered client", response = ClientSimpleDTO.class),
            @ApiResponse(code=400, message="Invalid Input. To register an individual, fill " +
                    "in the fields " +
                    "'name', 'cpf' and 'phoneNumber'. To register a legal entity, fill in 'socialName', 'cnpj' and " +
                    "'phoneNumber'."),
    })
    public Response register(@Valid ClientSimpleDTO clientSimpleDTO, @Context UriInfo uriInfo) throws BusinessException {
        ClientSimpleDTO register = clientBean.register(clientSimpleDTO);
        return Response.created(buildURI(uriInfo, register.getExternalId())).entity(register).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{externalId}")
    @ApiOperation(value = "Detail a client")
    @ApiResponses({
            @ApiResponse(code=200, message="Detail", response = ClientDetailDTO.class),
            @ApiResponse(code=404, message="No results found."),
    })
    public Response detail(@PathParam("externalId") String externalId) throws BusinessException {
        return Response.ok().entity(clientBean.detail(externalId)).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{externalId}")
    @ApiOperation(value = "Update a client")
    @ApiResponses({
            @ApiResponse(code=200, message="Customer data has been updated.", response = MessageDTO.class),
            @ApiResponse(code=404, message="No results found."),
            @ApiResponse(code=400, message="A bank account has already been created for this customer. Try migrating account type. | Invalid Input. " +
                    "To register an individual, fill in the fields 'name', 'cpf' and 'phoneNumber'. " +
                    "To register a legal entity, fill in 'socialName', 'cnpj' and 'phoneNumber'.")

    })
    public Response update(@Valid ClientSimpleDTO clientSimpleDTO, @PathParam("externalId") String externalId) throws BusinessException {
        return Response.ok().entity(clientBean.update(externalId, clientSimpleDTO)).build();
    }


}
