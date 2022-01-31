package dev.diegofernando.banktransactionmanager.resources;

import dev.diegofernando.banktransactionmanager.exception.BusinessException;
import dev.diegofernando.banktransactionmanager.util.BusinessMessageUtils;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

public abstract class AbstractResource {

    protected URI buildURI(UriInfo uriInfo, String path){
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        uriBuilder.path(String.valueOf(path));
        return uriBuilder.build();
    }

    protected void validateSizeAndPage(int size, int page) throws BusinessException {
        if (size <= 0)
            throw new BusinessException(BusinessMessageUtils.ERROR_NUM_SIZE_PAGE);
        if (page <= 0)
            throw new BusinessException(BusinessMessageUtils.ERROR_NUM_PAGE);
    }
}
