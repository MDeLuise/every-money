package com.github.mdeluise.everymoney.category.subcategory;

import com.github.mdeluise.everymoney.common.AbstractCrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/sub_category")
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
    public ResponseEntity<Collection<SubCategoryDTO>> findAll() {
        Set<SubCategoryDTO> result =
            subCategoryService.getAll().stream().map(subCategoryDTOConverter::convertToDTO).collect(Collectors.toSet());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<SubCategoryDTO> find(Long id) {
        SubCategoryDTO result = subCategoryDTOConverter.convertToDTO(subCategoryService.get(id));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<SubCategoryDTO> update(SubCategoryDTO updatedEntity, Long id) {
        SubCategoryDTO result = subCategoryDTOConverter.convertToDTO(
            subCategoryService.update(id, subCategoryDTOConverter.convertFromDTO(updatedEntity)));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @Override
    public void remove(Long id) {
        subCategoryService.remove(id);
    }


    @Override
    public ResponseEntity<SubCategoryDTO> save(SubCategoryDTO entityToSave) {
        SubCategoryDTO result = subCategoryDTOConverter.convertToDTO(
            subCategoryService.save(subCategoryDTOConverter.convertFromDTO(entityToSave)));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
