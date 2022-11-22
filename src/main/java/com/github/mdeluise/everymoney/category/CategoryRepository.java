package com.github.mdeluise.everymoney.category;

import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    @Override
    Collection<Category> findAll();
}
