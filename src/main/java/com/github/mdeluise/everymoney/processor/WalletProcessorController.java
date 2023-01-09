package com.github.mdeluise.everymoney.processor;

import com.github.mdeluise.everymoney.movements.income.IncomeDTO;
import com.github.mdeluise.everymoney.movements.income.IncomeDTOConverter;
import com.github.mdeluise.everymoney.movements.outcome.OutcomeDTO;
import com.github.mdeluise.everymoney.movements.outcome.OutcomeDTOConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/stats")
@Tag(name = "Statistics", description = "Endpoints for statistics")
public class WalletProcessorController {
    private final WalletProcessor walletProcessor;
    private final OutcomeDTOConverter outcomeDtoConverter;
    private final IncomeDTOConverter incomeDtoConverter;


    @Autowired
    public WalletProcessorController(WalletProcessor walletProcessor, OutcomeDTOConverter outcomeDtoConverter,
                                     IncomeDTOConverter incomeDtoConverter) {
        this.walletProcessor = walletProcessor;
        this.outcomeDtoConverter = outcomeDtoConverter;
        this.incomeDtoConverter = incomeDtoConverter;
    }


    @GetMapping("/balance/{id}")
    @Operation(
        summary = "Get the balance of a single Wallet",
        description = "Get the balance of a single Wallet, according to the `id` parameter."
    )
    public ResponseEntity<Double> getBalance(@PathVariable("id") long walletId) {
        return new ResponseEntity<>(walletProcessor.getBalance(walletId), HttpStatus.OK);
    }


    @GetMapping("/outcome/{id}/{start}/{end}")
    @Operation(
        summary = "Get the list of Outcomes of all Wallets",
        description = "Get the list of Outcomes of all Wallets, according to the `id` parameter," +
                          "the `start` date and the `end` date."
    )
    public ResponseEntity<Collection<OutcomeDTO>> getOutcomes(@PathVariable("id") long walletId,
                                                              @PathVariable Optional<Instant> start,
                                                              @PathVariable Optional<Instant> end) {
        Instant periodStart = start.orElse(Instant.MIN);
        Instant periodEnd = end.orElse(Instant.MAX);
        Set<OutcomeDTO> result = walletProcessor.getOutcomes(walletId, periodStart, periodEnd, true).stream()
                                                .map(outcomeDtoConverter::convertToDTO)
                                                .collect(Collectors.toSet());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @GetMapping("/income/{id}/{start}/{end}")
    @Operation(
        summary = "Get the list of Incomes of all Wallets",
        description = "Get the list of Incomes of all Wallets, according to the `id` parameter," +
                          "the `start` date and the `end` date."
    )
    public ResponseEntity<Collection<IncomeDTO>> getIncomes(@PathVariable("id") long walletId,
                                                            @PathVariable Optional<Instant> start,
                                                            @PathVariable Optional<Instant> end) {
        Instant periodStart = start.orElse(Instant.MIN);
        Instant periodEnd = end.orElse(Instant.MAX);
        Set<IncomeDTO> result = walletProcessor.getIncomes(walletId, periodStart, periodEnd, true).stream()
                                               .map(incomeDtoConverter::convertToDTO)
                                               .collect(Collectors.toSet());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @GetMapping("/net-income/{id}/{start}/{end}")
    @Operation(
        summary = "Get the net income of a single Wallet",
        description = "Get the net income of a single Wallet, according to the `id` parameter"
    )
    public ResponseEntity<Double> getNetIncome(@PathVariable("id") long walletId, @PathVariable Optional<Instant> start,
                                               @PathVariable Optional<Instant> end) {
        Instant periodStart = start.orElse(Instant.MIN);
        Instant periodEnd = end.orElse(Instant.MAX);
        return new ResponseEntity<>(walletProcessor.getNetIncome(walletId, periodStart, periodEnd), HttpStatus.OK);
    }


    @GetMapping("/compare/net-income/{id}/{first}/{second}")
    @Operation(
        summary = "Compare the monthly net income of a single Wallet",
        description = "Compare the monthly net income of a single Wallet, according to the `id` parameter," +
                          "the `first` month/year and the `second` month/year."
    )
    public ResponseEntity<Double> compareMonthlyNetIncome(@PathVariable("id") long walletId,
                                                          @PathVariable YearMonth first,
                                                          @PathVariable YearMonth second) {
        return new ResponseEntity<>(
            walletProcessor.getNetIncomeMonthComparison(walletId, first, second), HttpStatus.OK);
    }


    @GetMapping("/outcome/mean/{id}/{first}/{second}")
    @Operation(
        summary = "Get the monthly Outcome mean of a single Wallet",
        description = "Get the monthly outcome mean of a single Wallet, according to the `id` parameter," +
                          "the `first` month/year and the `second` month/year."
    )
    public ResponseEntity<Double> getMonthlyOutcomeMean(@PathVariable("id") long walletId,
                                                        @PathVariable YearMonth first, @PathVariable YearMonth second) {
        return new ResponseEntity<>(walletProcessor.getMonthlyOutcomeMean(walletId, first, second), HttpStatus.OK);
    }


    @GetMapping("/income/mean/{id}/{first}/{second}")
    @Operation(
        summary = "Get the monthly income mean of a single Wallet",
        description = "Get the monthly income mean of a single Wallet, according to the `id` parameter," +
                          "the `first` month/year and the `second` month/year."
    )
    public ResponseEntity<Double> getMonthlyIncomeMean(@PathVariable("id") long walletId, @PathVariable YearMonth first,
                                                       @PathVariable YearMonth second) {
        return new ResponseEntity<>(walletProcessor.getMonthlyIncomeMean(walletId, first, second), HttpStatus.OK);
    }


    @GetMapping("/categories/mean/{id}/{first}/{second}")
    @Operation(
        summary = "Get monthly mean about categories of a single Wallet",
        description = "Get the monthly mean about categories of a single Wallet, according to the `id` parameter," +
                          "the `first` month/year and the `second` month/year."
    )
    public ResponseEntity<Collection<CategoryStatistic>> getMonthlyOutcomeCategoriesMean(
        @PathVariable("id") long walletId, @PathVariable YearMonth first, @PathVariable YearMonth second) {
        return new ResponseEntity<>(
            walletProcessor.getOutcomeCategoriesMonthlyMean(walletId, first, second), HttpStatus.OK);
    }


    @GetMapping("/categories/total/{id}/{first}/{second}")
    @Operation(
        summary = "Get monthly total about categories of a single Wallet",
        description = "Get the monthly total about categories of a single Wallet, according to the `id` parameter," +
                          "the `first` month/year and the `second` month/year."
    )
    public ResponseEntity<Collection<CategoryStatistic>> getMonthlyOutcomeCategoriesTotal(
        @PathVariable("id") long walletId, @PathVariable YearMonth first, @PathVariable YearMonth second) {
        return new ResponseEntity<>(walletProcessor.getOutcomeCategoriesTotal(walletId, first, second), HttpStatus.OK);
    }
}
