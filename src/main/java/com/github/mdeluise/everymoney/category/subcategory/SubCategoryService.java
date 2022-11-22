package com.github.mdeluise.everymoney.category.subcategory;

import com.github.mdeluise.everymoney.common.AbstractCrudService;
import com.github.mdeluise.everymoney.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SubCategoryService extends AbstractCrudService<SubCategory, Long> {
    public SubCategoryService(SubCategoryRepository repository) {
        super(repository);
    }


    @Override
    public Collection<SubCategory> getAll() {
        return ((SubCategoryRepository) repository).findAll();
    }


    @Override
    public SubCategory get(Long id) {
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
    public SubCategory save(SubCategory entityToSave) {
        return repository.save(entityToSave);
    }


    @Override
    public SubCategory update(Long id, SubCategory updatedEntity) {
        SubCategory toUpdate = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
        toUpdate.setCategory(updatedEntity.getCategory());
        toUpdate.setDescription(updatedEntity.getDescription());
        toUpdate.setName(updatedEntity.getName());
        toUpdate.setOutcomes(updatedEntity.getOutcomes());
        return repository.save(toUpdate);
    }
}
