package dev.diegofernando.banktransactionmanager.dto.bankaccount.mapper;

import dev.diegofernando.banktransactionmanager.dto.bankaccount.BankAccountOpeningResultDTO;
import dev.diegofernando.banktransactionmanager.model.BankAccount;
import org.modelmapper.ModelMapper;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BankAccountDTOMapper {

    private ModelMapper modelMapper;

    @PostConstruct
    private void config() {
        this.modelMapper = new ModelMapper();
    }

    public BankAccountOpeningResultDTO openingObjToDTO(BankAccount obj){
        return modelMapper.map(obj, BankAccountOpeningResultDTO.class);
    }
}
