package dev.diegofernando.banktransactionmanager.dto.bankstatement.mapper;

import dev.diegofernando.banktransactionmanager.dto.bankstatement.BankStatementMovimentationDTO;
import dev.diegofernando.banktransactionmanager.model.BankStatement;
import org.modelmapper.ModelMapper;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BankStatementDTOMapper {
    private ModelMapper modelMapper;

    @PostConstruct
    private void config() {
        this.modelMapper = new ModelMapper();
    }

    public BankStatementMovimentationDTO objToDTO(BankStatement obj){
        return modelMapper.map(obj, BankStatementMovimentationDTO.class);
    }
}
