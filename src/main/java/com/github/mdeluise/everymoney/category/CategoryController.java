package com.github.mdeluise.everymoney.category;

import com.github.mdeluise.everymoney.category.subcategory.SubCategoryDTO;
import com.github.mdeluise.everymoney.category.subcategory.SubCategoryDTOConverter;
import com.github.mdeluise.everymoney.common.AbstractCrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/category")
public class CategoryController implements AbstractCrudController<CategoryDTO, Long> {
    private final CategoryService categoryService;
    private final SubCategoryDTOConverter subCategoryDTOConverter;
    private final CategoryDTOConverter categoryDtoConverter;


    @Autowired
    public CategoryController(CategoryService categoryService, SubCategoryDTOConverter subCategoryDTOConverter,
                              CategoryDTOConverter categoryDtoConverter) {
        this.categoryService = categoryService;
        this.subCategoryDTOConverter = subCategoryDTOConverter;
        this.categoryDtoConverter = categoryDtoConverter;
    }


    @Override
    public ResponseEntity<Collection<CategoryDTO>> findAll() {
        Set<CategoryDTO> result =
            categoryService.getAll().stream()
                           .map(categoryDtoConverter::convertToDTO)
                           .collect(Collectors.toSet());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<CategoryDTO> find(Long id) {
        CategoryDTO result = categoryDtoConverter.convertToDTO(categoryService.get(id));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<CategoryDTO> update(CategoryDTO updatedEntity, Long id) {
        CategoryDTO result = categoryDtoConverter.convertToDTO(
            categoryService.update(id, categoryDtoConverter.convertFromDTO(updatedEntity)));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @Override
    public void remove(Long id) {
        categoryService.remove(id);
    }


    @Override
    public ResponseEntity<CategoryDTO> save(CategoryDTO entityToSave) {
        CategoryDTO result =
            categoryDtoConverter.convertToDTO(categoryService.save(categoryDtoConverter.convertFromDTO(entityToSave)));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @GetMapping("/{id}/sub_categories")
    public ResponseEntity<Collection<SubCategoryDTO>> getAllSubCategories(@PathVariable long id) {
        Set<SubCategoryDTO> result =
            categoryService.get(id).getSubCategories().stream()
                           .map(subCategoryDTOConverter::convertToDTO)
                           .collect(Collectors.toSet());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
