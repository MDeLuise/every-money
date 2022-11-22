package com.github.mdeluise.everymoney.movements.income;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface IncomeRepository extends CrudRepository<Income, Long> {
    @Override
    Collection<Income> findAll();
}
