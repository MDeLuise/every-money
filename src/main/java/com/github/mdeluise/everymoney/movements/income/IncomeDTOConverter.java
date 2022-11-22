package com.github.mdeluise.everymoney.movements.income;

import com.github.mdeluise.everymoney.common.AbstractDTOConverter;
import com.github.mdeluise.everymoney.wallet.WalletService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class IncomeDTOConverter extends AbstractDTOConverter<Income, IncomeDTO> {
    private final ModelMapper modelMapper;
    private final WalletService walletService;


    @Autowired
    public IncomeDTOConverter(ModelMapper modelMapper, WalletService walletService) {
        this.modelMapper = modelMapper;
        this.walletService = walletService;
    }


    @Override
    public Income convertFromDTO(IncomeDTO incomeDTO) {
        Income income = modelMapper.map(incomeDTO, Income.class);
        income.setWallet(walletService.get(incomeDTO.getWalletId()));
        return income;
    }


    @Override
    public IncomeDTO convertToDTO(Income income) {
        IncomeDTO incomeDTO = modelMapper.map(income, IncomeDTO.class);
        if (income.getCounterpart().isPresent()) {
            incomeDTO.setTransferCounterpartId(Optional.of(income.getCounterpart().get().getId()));
        }
        return incomeDTO;
    }
}
