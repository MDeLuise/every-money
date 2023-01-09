package com.github.mdeluise.everymoney.movements.income;

import com.github.mdeluise.everymoney.common.AbstractCrudController;
import com.github.mdeluise.everymoney.wallet.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/income")
@Tag(name = "Income", description = "Endpoints for CRUD operations on incomes")
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
    @Operation(
        summary = "Get all the Income",
        description = "Get all the Income."
    )
    public ResponseEntity<Collection<IncomeDTO>> findAll() {
        Set<IncomeDTO> result = incomeService.getAll().stream().map(this::convertToDTO).collect(Collectors.toSet());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @Override
    @Operation(
        summary = "Get a single Income",
        description = "Get the details of a given Income, according to the `id` parameter."
    )
    public ResponseEntity<IncomeDTO> find(
        @Parameter(description = "The ID of the Income on which to perform the operation") Long id) {
        return new ResponseEntity<>(convertToDTO(incomeService.get(id)), HttpStatus.OK);
    }


    @Override
    @Operation(
        summary = "Update a single Income",
        description = "Update the details of a given Income, according to the `id` parameter." +
                          "Please note that some fields may be readonly for integrity purposes."
    )
    public ResponseEntity<IncomeDTO> update(IncomeDTO updatedEntity, @Parameter(
        description = "The ID of the Income on which to perform the operation") Long id) {
        Income toReturn = incomeService.update(id, convertToEntity(updatedEntity));
        return new ResponseEntity<>(convertToDTO(toReturn), HttpStatus.OK);
    }


    @Override
    @Operation(
        summary = "Delete a single Income",
        description = "Delete the given Income, according to the `id` parameter."
    )
    public void remove(@Parameter(description = "The ID of the Income on which to perform the operation") Long id) {
        incomeService.remove(id);
    }


    @Override
    @Operation(
        summary = "Create a new Income",
        description = "Create a new Income."
    )
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
