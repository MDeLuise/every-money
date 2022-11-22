package com.github.mdeluise.everymoney.category.subcategory;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface SubCategoryRepository extends CrudRepository<SubCategory, Long> {
    @Override
    Collection<SubCategory> findAll();
}
