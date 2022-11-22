package com.github.mdeluise.everymoney.movement.outcome.service;

import com.github.mdeluise.everymoney.exception.EntityNotFoundException;
import com.github.mdeluise.everymoney.movements.outcome.Outcome;
import com.github.mdeluise.everymoney.movements.outcome.OutcomeRepository;
import com.github.mdeluise.everymoney.movements.outcome.OutcomeService;
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
public class OutcomeServiceTest {
    @Mock
    OutcomeRepository outcomeRepository;
    @InjectMocks
    OutcomeService outcomeService;


    @Test
    void whenSaveOutcome_thenReturnOutcome() {
        Outcome toSave = new Outcome();
        toSave.setId(0L);
        toSave.setDescription("outcome0");
        Mockito.when(outcomeRepository.save(toSave)).thenReturn(toSave);

        Assertions.assertThat(outcomeService.save(toSave)).isSameAs(toSave);
    }


    @Test
    void whenGetOutcome_thenReturnOutcome() {
        Outcome toGet = new Outcome();
        toGet.setId(0L);
        toGet.setDescription("outcome0");
        Mockito.when(outcomeRepository.findById(0L)).thenReturn(Optional.of(toGet));

        Assertions.assertThat(outcomeService.get(0L)).isSameAs(toGet);
    }


    @Test
    void whenGetNonExistingOutcome_thenError() {
        Mockito.when(outcomeRepository.findById(0L)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> outcomeService.get(0L))
                  .isInstanceOf(EntityNotFoundException.class);
    }


    @Test
    void whenGetAllOutcomes_thenReturnAllOutcomes() {
        Outcome toGet1 = new Outcome();
        toGet1.setId(0L);
        toGet1.setDescription("outcome0");
        Outcome toGet2 = new Outcome();
        toGet2.setId(1L);
        toGet2.setDescription("outcome1");

        Set<Outcome> allOutcomes = Set.of(toGet1, toGet2);
        Mockito.when(outcomeRepository.findAll()).thenReturn(allOutcomes);

        Assertions.assertThat(outcomeService.getAll()).isSameAs(allOutcomes);
    }


    @Test
    void givenOutcome_whenDeleteOutcome_thenDeleteOutcome() {
        Mockito.when(outcomeRepository.existsById(0L)).thenReturn(true);
        outcomeService.remove(0L);
        Mockito.verify(outcomeRepository, Mockito.times(1)).deleteById(0L);
    }


    @Test
    void whenDeleteNonExistingOutcome_thenError() {
        Mockito.when(outcomeRepository.existsById(0L)).thenReturn(false);

        Assertions.assertThatThrownBy(() -> outcomeService.remove(0L)).isInstanceOf(
            EntityNotFoundException.class);
    }


    @Test
    void givenOutcome_whenUpdateOutcome_thenUpdateOutcome() {
        Outcome updated = new Outcome();
        updated.setDescription("updated");
        Mockito.when(outcomeRepository.existsById(0L)).thenReturn(true);
        Mockito.when(outcomeRepository.findById(0L)).thenReturn(Optional.of(new Outcome()));
        Mockito.when(outcomeRepository.save(Mockito.any())).thenReturn(updated);

        Assertions.assertThat(outcomeService.update(0L, updated)).isSameAs(updated);
    }


    @Test
    void whenUpdateNonExistingOutcome_thenError() {
        Mockito.when(outcomeRepository.existsById(0L)).thenReturn(false);
        Outcome updated = new Outcome();
        updated.setDescription("updated");

        Assertions.assertThatThrownBy(() -> outcomeService.update(0L, updated)).isInstanceOf(
            EntityNotFoundException.class);
    }

}
