package com.github.mdeluise.everymoney.processor;

import com.github.mdeluise.everymoney.movements.income.Income;
import com.github.mdeluise.everymoney.movements.outcome.Outcome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.YearMonth;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/stats")
public class WalletProcessorController {
    private final WalletProcessor walletProcessor;


    @Autowired
    public WalletProcessorController(WalletProcessor walletProcessor) {
        this.walletProcessor = walletProcessor;
    }


    @GetMapping("/balance/{id}")
    public ResponseEntity<Double> getBalance(@PathVariable("id") long walletId) {
        return new ResponseEntity<>(walletProcessor.getBalance(walletId), HttpStatus.OK);
    }


    @GetMapping("/outcome/{id}/{start}/{end}")
    public ResponseEntity<Collection<Outcome>> getOutcomes(@PathVariable("id") long walletId,
                                                           @PathVariable Optional<Instant> start,
                                                           @PathVariable Optional<Instant> end) {
        Instant periodStart = start.orElse(Instant.MIN);
        Instant periodEnd = end.orElse(Instant.MAX);
        return new ResponseEntity<>(walletProcessor.getOutcomes(walletId, periodStart, periodEnd, true), HttpStatus.OK);
    }


    @GetMapping("/income/{id}/{start}/{end}")
    public ResponseEntity<Collection<Income>> getIncomes(@PathVariable("id") long walletId,
                                                         @PathVariable Optional<Instant> start,
                                                         @PathVariable Optional<Instant> end) {
        Instant periodStart = start.orElse(Instant.MIN);
        Instant periodEnd = end.orElse(Instant.MAX);
        return new ResponseEntity<>(walletProcessor.getIncomes(walletId, periodStart, periodEnd, true), HttpStatus.OK);
    }


    @GetMapping("/net-income/{id}/{start}/{end}")
    public ResponseEntity<Double> getNetIncome(@PathVariable("id") long walletId, @PathVariable Optional<Instant> start,
                                               @PathVariable Optional<Instant> end) {
        Instant periodStart = start.orElse(Instant.MIN);
        Instant periodEnd = end.orElse(Instant.MAX);
        return new ResponseEntity<>(walletProcessor.getNetIncome(walletId, periodStart, periodEnd), HttpStatus.OK);
    }


    @GetMapping("/compare/net-income/{id}/{first}/{second}")
    public ResponseEntity<Double> compareMonthlyNetIncome(@PathVariable("id") long walletId,
                                                          @PathVariable YearMonth first,
                                                          @PathVariable YearMonth second) {
        return new ResponseEntity<>(
            walletProcessor.getNetIncomeMonthComparison(walletId, first, second), HttpStatus.OK);
    }


    @GetMapping("/outcome/mean/{id}/{first}/{second}")
    public ResponseEntity<Double> getMonthlyOutcomeMean(@PathVariable("id") long walletId,
                                                        @PathVariable YearMonth first, @PathVariable YearMonth second) {
        return new ResponseEntity<>(walletProcessor.getMonthlyOutcomeMean(walletId, first, second), HttpStatus.OK);
    }


    @GetMapping("/income/mean/{id}/{first}/{second}")
    public ResponseEntity<Double> getMonthlyIncomeMean(@PathVariable("id") long walletId, @PathVariable YearMonth first,
                                                       @PathVariable YearMonth second) {
        return new ResponseEntity<>(walletProcessor.getMonthlyIncomeMean(walletId, first, second), HttpStatus.OK);
    }


    @GetMapping("/categories/mean/{id}/{first}/{second}")
    public ResponseEntity<Collection<CategoryStatistic>> getMonthlyOutcomeCategoriesMean(
        @PathVariable("id") long walletId, @PathVariable YearMonth first, @PathVariable YearMonth second) {
        return new ResponseEntity<>(
            walletProcessor.getOutcomeCategoriesMonthlyMean(walletId, first, second), HttpStatus.OK);
    }


    @GetMapping("/categories/total/{id}/{first}/{second}")
    public ResponseEntity<Collection<CategoryStatistic>> getMonthlyOutcomeCategoriesTotal(
        @PathVariable("id") long walletId, @PathVariable YearMonth first, @PathVariable YearMonth second) {
        return new ResponseEntity<>(walletProcessor.getOutcomeCategoriesTotal(walletId, first, second), HttpStatus.OK);
    }
}
