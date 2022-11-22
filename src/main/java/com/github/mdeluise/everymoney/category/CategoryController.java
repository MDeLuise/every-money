package com.github.mdeluise.everymoney.category;

import com.github.mdeluise.everymoney.common.AbstractCrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
@RequestMapping("/category")
public class CategoryController implements AbstractCrudController<Category, Long> {
    private final CategoryService categoryService;


    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @Override
    public ResponseEntity<Collection<Category>> findAll() {
        return new ResponseEntity<>(categoryService.getAll(), HttpStatus.OK);
    }


    @Override
    public ResponseEntity<Category> find(Long id) {
        return new ResponseEntity<>(categoryService.get(id), HttpStatus.OK);
    }


    @Override
    public ResponseEntity<Category> update(Category updatedEntity, Long id) {
        return new ResponseEntity<>(categoryService.update(id, updatedEntity), HttpStatus.OK);
    }


    @Override
    public void remove(Long id) {
        categoryService.remove(id);
    }


    @Override
    public ResponseEntity<Category> save(Category entityToSave) {
        return new ResponseEntity<>(categoryService.save(entityToSave), HttpStatus.OK);
    }
}
