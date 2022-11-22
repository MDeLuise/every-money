package com.github.mdeluise.everymoney.category;

import com.github.mdeluise.everymoney.category.subcategory.SubCategoryDTOConverter;
import com.github.mdeluise.everymoney.common.AbstractDTOConverter;
import com.github.mdeluise.everymoney.movements.outcome.OutcomeDTOConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CategoryDTOConverter extends AbstractDTOConverter<Category, CategoryDTO> {
    private final ModelMapper modelMapper;
    private final OutcomeDTOConverter outcomeDtoConverter;
    private final SubCategoryDTOConverter subCategoryDtoConverter;


    @Autowired
    public CategoryDTOConverter(ModelMapper modelMapper, OutcomeDTOConverter outcomeDtoConverter,
                                SubCategoryDTOConverter subCategoryDtoConverter) {
        this.modelMapper = modelMapper;
        this.outcomeDtoConverter = outcomeDtoConverter;
        this.subCategoryDtoConverter = subCategoryDtoConverter;
    }


    @Override
    public Category convertFromDTO(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        category.setOutcomes(categoryDTO.getOutcomes().stream()
                                        .map(outcomeDtoConverter::convertFromDTO)
                                        .collect(Collectors.toSet())
        );
        category.setSubCategories(categoryDTO.getSubCategories().stream()
                                             .map(subCategoryDtoConverter::convertFromDTO)
                                             .collect(Collectors.toSet())
        );
        return category;
    }


    @Override
    public CategoryDTO convertToDTO(Category category) {
        CategoryDTO categoryDTO = modelMapper.map(category, CategoryDTO.class);
        categoryDTO.setOutcomes(category.getOutcomes().stream()
                                        .map(outcomeDtoConverter::convertToDTO)
                                        .collect(Collectors.toSet())
        );
        categoryDTO.setSubCategories(category.getSubCategories().stream()
                                             .map(subCategoryDtoConverter::convertToDTO)
                                             .collect(Collectors.toSet())
        );
        return categoryDTO;
    }
}
