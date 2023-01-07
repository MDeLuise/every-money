package com.github.mdeluise.everymoney.category;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
    @Override
    Collection<Category> findAll();
}
