package com.github.mdeluise.everymoney.category.subcategory;

import com.github.mdeluise.everymoney.category.CategoryService;
import com.github.mdeluise.everymoney.common.AbstractDTOConverter;
import com.github.mdeluise.everymoney.movements.outcome.OutcomeDTOConverter;
import com.github.mdeluise.everymoney.movements.outcome.OutcomeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class SubCategoryDTOConverter extends AbstractDTOConverter<SubCategory, SubCategoryDTO> {
    private final ModelMapper modelMapper;
    private final CategoryService categoryService;
    private final OutcomeService outcomeService;
    private final OutcomeDTOConverter outcomeDTOConverter;


    @Autowired
    public SubCategoryDTOConverter(ModelMapper modelMapper, CategoryService categoryService,
                                   OutcomeService outcomeService, OutcomeDTOConverter outcomeDTOConverter) {
        this.modelMapper = modelMapper;
        this.categoryService = categoryService;
        this.outcomeService = outcomeService;
        this.outcomeDTOConverter = outcomeDTOConverter;
    }


    @Override
    public SubCategory convertFromDTO(SubCategoryDTO subCategoryDTO) {
        SubCategory subCategory = modelMapper.map(subCategoryDTO, SubCategory.class);
        subCategory.setCategory(categoryService.get(subCategoryDTO.getCategoryId()));
        subCategory.setOutcomes(
            subCategoryDTO.getOutcomes().stream()
                          .map(outcomeDTO -> outcomeService.get(outcomeDTO.getId()))
                          .collect(Collectors.toSet()));
        return subCategory;
    }


    @Override
    public SubCategoryDTO convertToDTO(SubCategory subCategory) {
        SubCategoryDTO subCategoryDTO = modelMapper.map(subCategory, SubCategoryDTO.class);
        subCategoryDTO.setOutcomes(
            subCategory.getOutcomes().stream()
                       .map(outcomeDTOConverter::convertToDTO)
                       .collect(Collectors.toSet()));
        return subCategoryDTO;
    }
}
