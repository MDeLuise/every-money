package com.github.mdeluise.everymoney.movements.outcome;

import com.github.mdeluise.everymoney.common.AbstractCrudService;
import com.github.mdeluise.everymoney.exception.EntityNotFoundException;
import com.github.mdeluise.everymoney.movements.income.Income;
import com.github.mdeluise.everymoney.movements.income.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class OutcomeService extends AbstractCrudService<Outcome, Long> {
    private final IncomeRepository incomeRepository;


    @Autowired
    public OutcomeService(OutcomeRepository repository, IncomeRepository incomeRepository) {
        super(repository);
        this.incomeRepository = incomeRepository;
    }


    @Override
    public Collection<Outcome> getAll() {
        return ((OutcomeRepository) repository).findAll();
    }


    @Override
    public Outcome get(Long id) {
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
    public Outcome save(Outcome entityToSave) {
        Outcome saved = repository.save(entityToSave);
        if (saved.getCounterpart() != null) {
            saved.getCounterpart().setCounterpart(saved);
            incomeRepository.save((Income) saved.getCounterpart());
        }
        return saved;
    }


    @Override
    public Outcome update(Long id, Outcome updatedEntity) {
        Outcome toUpdate = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
        toUpdate.setAmount(updatedEntity.getAmount());
        toUpdate.setDescription(updatedEntity.getDescription());
        toUpdate.setWallet(updatedEntity.getWallet());
        toUpdate.setCategory(updatedEntity.getCategory());
        toUpdate.setDate(updatedEntity.getDate());
        return repository.save(toUpdate);
    }
}
