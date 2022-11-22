package com.github.mdeluise.everymoney.movements.income;

import com.github.mdeluise.everymoney.common.AbstractCrudController;
import com.github.mdeluise.everymoney.wallet.WalletService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/income")
public class IncomeController implements AbstractCrudController<IncomeDTO, Long> {
    private final IncomeService incomeService;
    private final ModelMapper modelMapper;
    private final WalletService walletService;


    @Autowired
    public IncomeController(IncomeService incomeService, ModelMapper modelMapper, WalletService walletService) {
        this.incomeService = incomeService;
        this.modelMapper = modelMapper;
        this.walletService = walletService;
    }


    @Override
    public ResponseEntity<Collection<IncomeDTO>> findAll() {
        Set<IncomeDTO> result = incomeService.getAll().stream().map(this::convertToDTO).collect(Collectors.toSet());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<IncomeDTO> find(Long id) {
        return new ResponseEntity<>(convertToDTO(incomeService.get(id)), HttpStatus.OK);
    }


    @Override
    public ResponseEntity<IncomeDTO> update(IncomeDTO updatedEntity, Long id) {
        Income toReturn = incomeService.update(id, convertToEntity(updatedEntity));
        return new ResponseEntity<>(convertToDTO(toReturn), HttpStatus.OK);
    }


    @Override
    public void remove(Long id) {
        incomeService.remove(id);
    }


    @Override
    public ResponseEntity<IncomeDTO> save(IncomeDTO entityToSave) {
        Income toReturn = incomeService.save(convertToEntity(entityToSave));
        return new ResponseEntity<>(convertToDTO(toReturn), HttpStatus.OK);
    }


    private Income convertToEntity(IncomeDTO incomeDTO) {
        Income income = modelMapper.map(incomeDTO, Income.class);
        income.setWallet(walletService.get(incomeDTO.getWalletId()));
        return income;
    }


    private IncomeDTO convertToDTO(Income income) {
        return modelMapper.map(income, IncomeDTO.class);
    }
}
