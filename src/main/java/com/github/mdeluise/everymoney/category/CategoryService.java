package com.github.mdeluise.everymoney.category;

import com.github.mdeluise.everymoney.common.AbstractCrudService;
import com.github.mdeluise.everymoney.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CategoryService extends AbstractCrudService<Category, Long> {

    @Autowired
    public CategoryService(CategoryRepository repository) {
        super(repository);
    }


    @Override
    public Collection<Category> getAll() {
        return ((CategoryRepository) repository).findAll();
    }


    @Override
    public Category get(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
    }


    @Override
    public void remove(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(id);
        }
        repository.deleteById(id);
    }


    @Override
    public Category save(Category entityToSave) {
        return repository.save(entityToSave);
    }


    @Override
    public Category update(Long id, Category updatedEntity) {
        Category toUpdate = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
        toUpdate.setName(updatedEntity.getName());
        toUpdate.setDescription(updatedEntity.getDescription());
        return repository.save(toUpdate);
    }
}
