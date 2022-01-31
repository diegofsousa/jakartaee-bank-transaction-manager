package dev.diegofernando.banktransactionmanager.dto.client.mapper;

import dev.diegofernando.banktransactionmanager.dto.client.ClientDetailDTO;
import dev.diegofernando.banktransactionmanager.dto.client.ClientSimpleDTO;
import dev.diegofernando.banktransactionmanager.model.Client;
import dev.diegofernando.banktransactionmanager.model.LegalEntity;
import dev.diegofernando.banktransactionmanager.model.NaturalPerson;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClientDTOMapper {

    private ModelMapper modelMapper;

    @PostConstruct
    private void config() {
        this.modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<ClientSimpleDTO, Client>() {
            @Override
            protected void configure() {
                skip(destination.getId());
            }
        });

        modelMapper.addMappings(new PropertyMap<NaturalPerson, ClientSimpleDTO>() {
            @Override
            protected void configure() {
                skip(destination.getCpf());
            }
        });

        modelMapper.addMappings(new PropertyMap<LegalEntity, ClientSimpleDTO>() {
            @Override
            protected void configure() {
                skip(destination.getCnpj());
                skip(destination.getName());
            }
        });

    }

    public ClientSimpleDTO registerObjToDTO(Client obj){
        return modelMapper.map(obj, ClientSimpleDTO.class);
    }

    public ClientDetailDTO detailObjToDTO(Client obj){
        return modelMapper.map(obj, ClientDetailDTO.class);
    }

}
