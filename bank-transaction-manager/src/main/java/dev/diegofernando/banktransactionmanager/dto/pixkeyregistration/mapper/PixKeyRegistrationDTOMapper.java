package dev.diegofernando.banktransactionmanager.dto.pixkeyregistration.mapper;

import dev.diegofernando.banktransactionmanager.dto.pixkeyregistration.PixKeyRegistrationDetailDTO;
import dev.diegofernando.banktransactionmanager.model.PixKeyRegistration;
import org.modelmapper.ModelMapper;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PixKeyRegistrationDTOMapper {
    private ModelMapper modelMapper;

    @PostConstruct
    private void config() {
        this.modelMapper = new ModelMapper();
    }

    public PixKeyRegistrationDetailDTO objToDTO(PixKeyRegistration obj){
        return modelMapper.map(obj, PixKeyRegistrationDetailDTO.class);
    }
}

