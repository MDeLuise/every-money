package com.github.mdeluise.everymoney.category.subcategory;

import com.github.mdeluise.everymoney.common.AbstractCrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
@RequestMapping("/sub_category")
public class SubCategoryController implements AbstractCrudController<SubCategory, Long> {
    private final SubCategoryService subCategoryService;


    @Autowired
    public SubCategoryController(SubCategoryService subCategoryService) {
        this.subCategoryService = subCategoryService;
    }


    @Override
    public ResponseEntity<Collection<SubCategory>> findAll() {
        return new ResponseEntity<>(subCategoryService.getAll(), HttpStatus.OK);
    }


    @Override
    public ResponseEntity<SubCategory> find(Long id) {
        return new ResponseEntity<>(subCategoryService.get(id), HttpStatus.OK);
    }


    @Override
    public ResponseEntity<SubCategory> update(SubCategory updatedEntity, Long id) {
        return new ResponseEntity<>(subCategoryService.update(id, updatedEntity), HttpStatus.OK);
    }


    @Override
    public void remove(Long id) {
        subCategoryService.remove(id);
    }


    @Override
    public ResponseEntity<SubCategory> save(SubCategory entityToSave) {
        return new ResponseEntity<>(subCategoryService.save(entityToSave), HttpStatus.OK);
    }
}
