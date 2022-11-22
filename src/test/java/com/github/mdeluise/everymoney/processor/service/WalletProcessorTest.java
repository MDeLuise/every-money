package com.github.mdeluise.everymoney.processor.service;

import com.github.mdeluise.everymoney.exception.EntityNotFoundException;
import com.github.mdeluise.everymoney.movements.AbstractMovement;
import com.github.mdeluise.everymoney.movements.income.Income;
import com.github.mdeluise.everymoney.movements.outcome.Outcome;
import com.github.mdeluise.everymoney.processor.WalletProcessor;
import com.github.mdeluise.everymoney.wallet.Wallet;
import com.github.mdeluise.everymoney.wallet.WalletService;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@ExtendWith(SpringExtension.class)
@WithMockUser(username = "admin", roles = "ADMIN")
public class WalletProcessorTest {
    @Mock
    WalletService walletService;
    @InjectMocks
    WalletProcessor walletProcessor;


    @Test
    void whenGetBalance_thenReturnBalance() {
        prepareEnvironment();
        Assertions.assertThat(walletProcessor.getBalance(0L)).isCloseTo(12.1, Percentage.withPercentage(0.01));
    }


    @Test
    void whenGetBalanceNotExistingWallet_thenReturnError() {
        Mockito.doThrow(EntityNotFoundException.class).when(walletService).get(0L);
        Assertions.assertThatThrownBy(() -> walletProcessor.getBalance(0L)).isInstanceOf(EntityNotFoundException.class);
    }


    @Test
    void whenGetOutcomes_thenReturnOutcomes() {
        prepareEnvironment();
        Set<Outcome> outcomes = walletService.get(0L).getMovements().stream()
                                             .filter(abstractMovement -> abstractMovement instanceof Outcome)
                                             .map(abstractMovement -> (Outcome) abstractMovement)
                                             .collect(Collectors.toSet());
        Assertions.assertThat(walletProcessor.getOutcomes(0L, Instant.MIN, Instant.MAX, true)).hasSize(2);
        Assertions.assertThat(walletProcessor.getOutcomes(0L, Instant.MIN, Instant.MAX, true)).containsAll(outcomes);
    }


    @Test
    void whenGetOutcomeNotExistingWallet_thenReturnError() {
        Mockito.doThrow(EntityNotFoundException.class).when(walletService).get(0L);
        Assertions.assertThatThrownBy(() -> walletProcessor.getOutcomes(0L, Instant.MIN, Instant.MAX, true))
                  .isInstanceOf(EntityNotFoundException.class);
    }


    @Test
    void whenGetOutcomesFilteredByDate_thenReturnCorrectOutcomes() {
        prepareEnvironment();
        Instant instantFilter = Instant.now().plus(1, ChronoUnit.DAYS);
        Set<Outcome> outcomes = walletService.get(0L).getMovements().stream()
                                             .filter(abstractMovement -> abstractMovement instanceof Outcome)
                                             .filter(abstractMovement -> abstractMovement.getDate().isBefore(instantFilter))
                                             .map(abstractMovement -> (Outcome) abstractMovement)
                                             .collect(Collectors.toSet());
        Assertions.assertThat(walletProcessor.getOutcomes(0L, Instant.MIN, instantFilter, true)).hasSize(1);
        Assertions.assertThat(walletProcessor.getOutcomes(0L, Instant.MIN, instantFilter, true)).containsAll(outcomes);
    }


    @Test
    void whenGetIncomes_thenReturnIncomes() {
        prepareEnvironment();
        Set<Income> incomes =
            walletService.get(0L).getMovements().stream().filter(abstractMovement -> abstractMovement instanceof Income)
                         .map(abstractMovement -> (Income) abstractMovement).collect(Collectors.toSet());
        Assertions.assertThat(walletProcessor.getIncomes(0L, Instant.MIN, Instant.MAX, true)).hasSize(2);
        Assertions.assertThat(walletProcessor.getIncomes(0L, Instant.MIN, Instant.MAX, true)).containsAll(incomes);
    }


    @Test
    void whenGetIncomeNotExistingWallet_thenReturnError() {
        Mockito.doThrow(EntityNotFoundException.class).when(walletService).get(0L);
        Assertions.assertThatThrownBy(() -> walletProcessor.getIncomes(0L, Instant.MIN, Instant.MAX, true))
                  .isInstanceOf(EntityNotFoundException.class);
    }


    @Test
    void whenGetIncomesFilteredByDate_thenReturnCorrectIncomes() {
        prepareEnvironment();
        Instant instantFilter = Instant.now().plus(1, ChronoUnit.DAYS);
        Set<Income> incomes =
            walletService.get(0L).getMovements().stream().filter(abstractMovement -> abstractMovement instanceof Income)
                         .filter(abstractMovement -> abstractMovement.getDate().isBefore(instantFilter))
                         .map(abstractMovement -> (Income) abstractMovement)
                         .collect(Collectors.toSet());
        Assertions.assertThat(walletProcessor.getIncomes(0L, Instant.MIN, instantFilter, true)).hasSize(1);
        Assertions.assertThat(walletProcessor.getIncomes(0L, Instant.MIN, instantFilter, true)).containsAll(incomes);
    }


    @Test
    void whenGetNetIncome_thenReturnNetIncome() {
        prepareEnvironment();
        Assertions.assertThat(walletProcessor.getNetIncome(0L, Instant.MIN, Instant.MAX))
                  .isCloseTo(11.6, Percentage.withPercentage(0.01));
    }


    private void prepareEnvironment() {
        Wallet wallet = new Wallet();
        wallet.setId(0L);
        wallet.setStartingAmount(.5);
        Mockito.when(walletService.get(0L)).thenReturn(wallet);

        Instant instant1 = Instant.now();
        Instant instant2 = instant1.plus(2, ChronoUnit.DAYS);
        Income income1 = new Income();
        income1.setId(0L);
        income1.setAmount(10.1);
        income1.setDate(instant1);
        Income income2 = new Income();
        income2.setId(1L);
        income2.setAmount(32);
        income2.setWallet(wallet);
        income2.setDate(instant2);
        Set<Income> incomes = Set.of(income1, income2);

        Outcome outcome1 = new Outcome();
        outcome1.setId(0L);
        outcome1.setAmount(10);
        outcome1.setDate(instant1);
        Outcome outcome2 = new Outcome();
        outcome2.setId(1L);
        outcome2.setAmount(20.5);
        outcome2.setWallet(wallet);
        outcome2.setDate(instant2);
        Set<Outcome> outcomes = Set.of(outcome1, outcome2);

        Set<AbstractMovement> allMovements = new HashSet<>(incomes);
        allMovements.addAll(outcomes);
        wallet.setMovements(allMovements);
    }


}
