package com.github.mdeluise.everymoney.movements.income;

import com.github.mdeluise.everymoney.common.AbstractCrudService;
import com.github.mdeluise.everymoney.exception.EntityNotFoundException;
import com.github.mdeluise.everymoney.movements.outcome.Outcome;
import com.github.mdeluise.everymoney.movements.outcome.OutcomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class IncomeService extends AbstractCrudService<Income, Long> {
    private final OutcomeRepository outcomeRepository;


    @Autowired
    public IncomeService(IncomeRepository incomeRepository, OutcomeRepository outcomeRepository) {
        super(incomeRepository);
        this.outcomeRepository = outcomeRepository;
    }


    @Override
    public Collection<Income> getAll() {
        return ((IncomeRepository) repository).findAll();
    }


    @Override
    public Income get(Long id) {
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
    public Income save(Income entityToSave) {
        Income saved = repository.save(entityToSave);
        if (saved.getCounterpart().isPresent()) {
            saved.getCounterpart().get().setCounterpart(saved);
            outcomeRepository.save((Outcome) saved.getCounterpart().get());
        }
        return saved;
    }


    @Override
    public Income update(Long id, Income updatedEntity) {
        Income toUpdate = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
        toUpdate.setAmount(updatedEntity.getAmount());
        toUpdate.setDescription(updatedEntity.getDescription());
        toUpdate.setWallet(updatedEntity.getWallet());
        toUpdate.setDate(updatedEntity.getDate());
        return repository.save(toUpdate);
    }
}
