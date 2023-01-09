package com.github.mdeluise.everymoney.category;

import com.github.mdeluise.everymoney.category.subcategory.SubCategoryDTO;
import com.github.mdeluise.everymoney.category.subcategory.SubCategoryDTOConverter;
import com.github.mdeluise.everymoney.common.AbstractCrudController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/category")
@Tag(name = "Category", description = "Endpoints for CRUD operations on categories")
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
    @Operation(
        summary = "Get all the Category",
        description = "Get all the Category."
    )
    public ResponseEntity<Collection<CategoryDTO>> findAll() {
        Set<CategoryDTO> result =
            categoryService.getAll().stream()
                           .map(categoryDtoConverter::convertToDTO)
                           .collect(Collectors.toSet());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @Override
    @Operation(
        summary = "Get a single Category",
        description = "Get the details of a given Category, according to the `id` parameter."
    )
    public ResponseEntity<CategoryDTO> find(
        @Parameter(description = "The ID of the Category on which to perform the operation") Long id) {
        CategoryDTO result = categoryDtoConverter.convertToDTO(categoryService.get(id));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @Override
    @Operation(
        summary = "Update a single Category",
        description = "Update the details of a given Category, according to the `id` parameter." +
                          "Please note that some fields may be readonly for integrity purposes."
    )
    public ResponseEntity<CategoryDTO> update(CategoryDTO updatedEntity, @Parameter(
        description = "The ID of the Category on which to perform the operation") Long id) {
        CategoryDTO result = categoryDtoConverter.convertToDTO(
            categoryService.update(id, categoryDtoConverter.convertFromDTO(updatedEntity)));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @Override
    @Operation(
        summary = "Delete a single Category",
        description = "Delete the given Category, according to the `id` parameter."
    )
    public void remove(@Parameter(description = "The ID of the Category on which to perform the operation") Long id) {
        categoryService.remove(id);
    }


    @Override
    @Operation(
        summary = "Create a new Category",
        description = "Create a new Category."
    )
    public ResponseEntity<CategoryDTO> save(CategoryDTO entityToSave) {
        CategoryDTO result =
            categoryDtoConverter.convertToDTO(categoryService.save(categoryDtoConverter.convertFromDTO(entityToSave)));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @GetMapping("/{id}/sub_categories")
    @Operation(
        summary = "Get all the subcategory",
        description = "Get all the subcategories of the Category with the given `id`."
    )
    public ResponseEntity<Collection<SubCategoryDTO>> getAllSubCategories(@PathVariable long id) {
        Set<SubCategoryDTO> result =
            categoryService.get(id).getSubCategories().stream()
                           .map(subCategoryDTOConverter::convertToDTO)
                           .collect(Collectors.toSet());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
