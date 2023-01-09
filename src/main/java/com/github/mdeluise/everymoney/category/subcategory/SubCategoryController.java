package com.github.mdeluise.everymoney.category.subcategory;

import com.github.mdeluise.everymoney.common.AbstractCrudController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sub_category")
@Tag(name = "SubCategory", description = "Endpoints for CRUD operations on sub categories")
public class SubCategoryController implements AbstractCrudController<SubCategoryDTO, Long> {
    private final SubCategoryService subCategoryService;
    private final SubCategoryDTOConverter subCategoryDTOConverter;


    @Autowired
    public SubCategoryController(SubCategoryService subCategoryService,
                                 SubCategoryDTOConverter subCategoryDTOConverter) {
        this.subCategoryService = subCategoryService;
        this.subCategoryDTOConverter = subCategoryDTOConverter;
    }


    @Override
    @Operation(
        summary = "Get all the SubCategory",
        description = "Get all the SubCategory."
    )
    public ResponseEntity<Collection<SubCategoryDTO>> findAll() {
        Set<SubCategoryDTO> result =
            subCategoryService.getAll().stream().map(subCategoryDTOConverter::convertToDTO).collect(Collectors.toSet());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @Override
    @Operation(
        summary = "Get a single SubCategory",
        description = "Get the details of a given SubCategory, according to the `id` parameter."
    )
    public ResponseEntity<SubCategoryDTO> find(
        @Parameter(description = "The ID of the SubCategory on which to perform the operation") Long id) {
        SubCategoryDTO result = subCategoryDTOConverter.convertToDTO(subCategoryService.get(id));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @Override
    @Operation(
        summary = "Update a single SubCategory",
        description = "Update the details of a given SubCategory, according to the `id` parameter." +
                          "Please note that some fields may be readonly for integrity purposes."
    )
    public ResponseEntity<SubCategoryDTO> update(SubCategoryDTO updatedEntity, @Parameter(
        description = "The ID of the SubCategory on which to perform the operation") Long id) {
        SubCategoryDTO result = subCategoryDTOConverter.convertToDTO(
            subCategoryService.update(id, subCategoryDTOConverter.convertFromDTO(updatedEntity)));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @Override
    @Operation(
        summary = "Delete a single SubCategory",
        description = "Delete the given SubCategory, according to the `id` parameter."
    )
    public void remove(
        @Parameter(description = "The ID of the SubCategory on which to perform the operation") Long id) {
        subCategoryService.remove(id);
    }


    @Override
    @Operation(
        summary = "Create a new SubCategory",
        description = "Create a new SubCategory."
    )
    public ResponseEntity<SubCategoryDTO> save(SubCategoryDTO entityToSave) {
        SubCategoryDTO result = subCategoryDTOConverter.convertToDTO(
            subCategoryService.save(subCategoryDTOConverter.convertFromDTO(entityToSave)));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
