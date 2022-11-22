package com.github.mdeluise.everymoney.movement.income.service;

import com.github.mdeluise.everymoney.exception.EntityNotFoundException;
import com.github.mdeluise.everymoney.movements.income.Income;
import com.github.mdeluise.everymoney.movements.income.IncomeRepository;
import com.github.mdeluise.everymoney.movements.income.IncomeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.Set;


@ExtendWith(SpringExtension.class)
@WithMockUser(username = "admin", roles = "ADMIN")
public class IncomeServiceTest {
    @Mock
    IncomeRepository incomeRepository;
    @InjectMocks
    IncomeService incomeService;


    @Test
    void whenSaveIncome_thenReturnIncome() {
        Income toSave = new Income();
        toSave.setId(0L);
        toSave.setDescription("income0");
        Mockito.when(incomeRepository.save(toSave)).thenReturn(toSave);

        Assertions.assertThat(incomeService.save(toSave)).isSameAs(toSave);
    }


    @Test
    void whenGetIncome_thenReturnIncome() {
        Income toGet = new Income();
        toGet.setId(0L);
        toGet.setDescription("income0");
        Mockito.when(incomeRepository.findById(0L)).thenReturn(Optional.of(toGet));

        Assertions.assertThat(incomeService.get(0L)).isSameAs(toGet);
    }


    @Test
    void whenGetNonExistingIncome_thenError() {
        Mockito.when(incomeRepository.findById(0L)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> incomeService.get(0L))
                  .isInstanceOf(EntityNotFoundException.class);
    }


    @Test
    void whenGetAllIncomes_thenReturnAllIncomes() {
        Income toGet1 = new Income();
        toGet1.setId(0L);
        toGet1.setDescription("income0");
        Income toGet2 = new Income();
        toGet2.setId(1L);
        toGet2.setDescription("income1");

        Set<Income> allIncomes = Set.of(toGet1, toGet2);
        Mockito.when(incomeRepository.findAll()).thenReturn(allIncomes);

        Assertions.assertThat(incomeService.getAll()).isSameAs(allIncomes);
    }


    @Test
    void givenIncome_whenDeleteIncome_thenDeleteIncome() {
        Mockito.when(incomeRepository.existsById(0L)).thenReturn(true);
        incomeService.remove(0L);
        Mockito.verify(incomeRepository, Mockito.times(1)).deleteById(0L);
    }


    @Test
    void whenDeleteNonExistingIncome_thenError() {
        Mockito.when(incomeRepository.existsById(0L)).thenReturn(false);

        Assertions.assertThatThrownBy(() -> incomeService.remove(0L)).isInstanceOf(
            EntityNotFoundException.class);
    }


    @Test
    void givenIncome_whenUpdateIncome_thenUpdateIncome() {
        Income updated = new Income();
        updated.setDescription("updated");
        Mockito.when(incomeRepository.existsById(0L)).thenReturn(true);
        Mockito.when(incomeRepository.findById(0L)).thenReturn(Optional.of(new Income()));
        Mockito.when(incomeRepository.save(Mockito.any())).thenReturn(updated);

        Assertions.assertThat(incomeService.update(0L, updated)).isSameAs(updated);
    }


    @Test
    void whenUpdateNonExistingIncome_thenError() {
        Mockito.when(incomeRepository.existsById(0L)).thenReturn(false);
        Income updated = new Income();
        updated.setDescription("updated");

        Assertions.assertThatThrownBy(() -> incomeService.update(0L, updated)).isInstanceOf(
            EntityNotFoundException.class);
    }

}
