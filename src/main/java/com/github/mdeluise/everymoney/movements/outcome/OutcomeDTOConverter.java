package com.github.mdeluise.everymoney.movements.outcome;

import com.github.mdeluise.everymoney.category.CategoryService;
import com.github.mdeluise.everymoney.category.subcategory.SubCategoryService;
import com.github.mdeluise.everymoney.common.AbstractDTOConverter;
import com.github.mdeluise.everymoney.wallet.WalletService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OutcomeDTOConverter extends AbstractDTOConverter<Outcome, OutcomeDTO> {
    private final ModelMapper modelMapper;
    private final WalletService walletService;
    private final CategoryService categoryService;
    private final SubCategoryService subCategoryService;


    @Autowired
    public OutcomeDTOConverter(ModelMapper modelMapper, WalletService walletService, CategoryService categoryService,
                               SubCategoryService subCategoryService) {
        this.modelMapper = modelMapper;
        this.walletService = walletService;
        this.categoryService = categoryService;
        this.subCategoryService = subCategoryService;
    }


    @Override
    public Outcome convertFromDTO(OutcomeDTO outcomeDTO) {
        Outcome outcome = modelMapper.map(outcomeDTO, Outcome.class);
        outcome.setWallet(walletService.get(outcomeDTO.getWalletId()));
        outcome.setCategory(categoryService.get(outcomeDTO.getCategoryId()));
        if (outcomeDTO.getSubCategoryId().isPresent()) {
            outcome.setSubCategory(subCategoryService.get(outcomeDTO.getSubCategoryId().get()));
        }
        return outcome;
    }


    @Override
    public OutcomeDTO convertToDTO(Outcome outcome) {
        return modelMapper.map(outcome, OutcomeDTO.class);
    }
}
