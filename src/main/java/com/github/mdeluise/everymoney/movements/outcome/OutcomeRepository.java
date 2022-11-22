package com.github.mdeluise.everymoney.movements.outcome;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface OutcomeRepository extends CrudRepository<Outcome, Long> {
    @Override
    Collection<Outcome> findAll();
}
