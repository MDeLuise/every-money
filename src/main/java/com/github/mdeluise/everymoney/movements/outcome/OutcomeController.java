package com.github.mdeluise.everymoney.movements.outcome;

import com.github.mdeluise.everymoney.category.CategoryService;
import com.github.mdeluise.everymoney.category.subcategory.SubCategoryService;
import com.github.mdeluise.everymoney.common.AbstractCrudController;
import com.github.mdeluise.everymoney.wallet.WalletService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/outcome")
public class OutcomeController implements AbstractCrudController<OutcomeDTO, Long> {
    private final OutcomeService outcomeService;
    private final WalletService walletService;
    private final CategoryService categoryService;
    private final SubCategoryService subCategoryService;
    private final ModelMapper modelMapper;


    @Autowired
    public OutcomeController(OutcomeService outcomeService, WalletService walletService,
                             CategoryService categoryService, SubCategoryService subCategoryService,
                             ModelMapper modelMapper) {
        this.outcomeService = outcomeService;
        this.walletService = walletService;
        this.categoryService = categoryService;
        this.subCategoryService = subCategoryService;
        this.modelMapper = modelMapper;
    }


    @Override
    public ResponseEntity<Collection<OutcomeDTO>> findAll() {
        Collection<OutcomeDTO> result =
            outcomeService.getAll().stream().map(this::convertToDTO).collect(Collectors.toSet());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<OutcomeDTO> find(Long id) {
        return new ResponseEntity<>(convertToDTO(outcomeService.get(id)), HttpStatus.OK);
    }


    @Override
    public ResponseEntity<OutcomeDTO> update(OutcomeDTO updatedEntity, Long id) {
        Outcome toReturn = outcomeService.update(id, convertToEntity(updatedEntity));
        return new ResponseEntity<>(convertToDTO(toReturn), HttpStatus.OK);
    }


    @Override
    public void remove(Long id) {
        outcomeService.remove(id);
    }


    @Override
    public ResponseEntity<OutcomeDTO> save(OutcomeDTO entityToSave) {
        Outcome toReturn = outcomeService.save(convertToEntity(entityToSave));
        return new ResponseEntity<>(convertToDTO(toReturn), HttpStatus.OK);
    }


    private Outcome convertToEntity(OutcomeDTO outcomeDTO) {
        Outcome outcome = modelMapper.map(outcomeDTO, Outcome.class);
        outcome.setWallet(walletService.get(outcomeDTO.getWalletId()));
        outcome.setCategory(categoryService.get(outcomeDTO.getCategoryId()));
        if (outcomeDTO.getSubCategoryId().isPresent()) {
            outcome.setSubCategory(subCategoryService.get(outcomeDTO.getSubCategoryId().get()));
        }
        return outcome;
    }


    private OutcomeDTO convertToDTO(Outcome outcome) {
        return modelMapper.map(outcome, OutcomeDTO.class);
    }
}
