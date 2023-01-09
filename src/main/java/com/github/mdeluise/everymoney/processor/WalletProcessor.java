package com.github.mdeluise.everymoney.processor;

import com.github.mdeluise.everymoney.category.Category;
import com.github.mdeluise.everymoney.movements.AbstractMovement;
import com.github.mdeluise.everymoney.movements.income.Income;
import com.github.mdeluise.everymoney.movements.outcome.Outcome;
import com.github.mdeluise.everymoney.wallet.Wallet;
import com.github.mdeluise.everymoney.wallet.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class WalletProcessor {
    private final WalletService walletService;


    @Autowired
    public WalletProcessor(WalletService walletService) {
        this.walletService = walletService;
    }


    public double getBalance(long walletId) {
        Wallet wallet = walletService.get(walletId);
        double balance = wallet.getStartingAmount();
        for (AbstractMovement movement : wallet.getMovements()) {
            if (movement instanceof Income) {
                balance += movement.getAmount();
            } else {
                balance -= movement.getAmount();
            }
        }
        return balance;
    }


    public double getTotalBalance(long... walletsId) {
        double totalBalance = 0;
        for (long walletId : walletsId) {
            totalBalance += getBalance(walletId);
        }
        return totalBalance;
    }


    public Set<Outcome> getOutcomes(long walletId, Instant periodStart, Instant periodEnd, boolean transfers) {
        Wallet wallet = walletService.get(walletId);
        return wallet.getMovements().stream().filter(movement -> movement instanceof Outcome)
                     .map(abstractMovement -> (Outcome) abstractMovement)
                     .filter(outcome -> outcome.getDate().isAfter(periodStart) && outcome.getDate().isBefore(periodEnd))
                     .filter(outcome -> transfers || outcome.getCounterpart().isEmpty())
                     .collect(Collectors.toSet());
    }


    public Set<Income> getIncomes(long walletId, Instant periodStart, Instant periodEnd, boolean transfers) {
        Wallet wallet = walletService.get(walletId);
        return wallet.getMovements().stream().filter(movement -> movement instanceof Income)
                     .map(abstractMovement -> (Income) abstractMovement)
                     .filter(income -> income.getDate().isAfter(periodStart) && income.getDate().isBefore(periodEnd))
                     .filter(income -> transfers || income.getCounterpart().isEmpty())
                     .collect(Collectors.toSet());
    }


    public double getNetIncome(long walletId, Instant periodStart, Instant periodEnd) {
        double income =
            getIncomes(walletId, periodStart, periodEnd, false).stream()
                                                               .mapToDouble(AbstractMovement::getAmount)
                                                               .sum();
        double outcome =
            getOutcomes(walletId, periodStart, periodEnd, false).stream()
                                                                .mapToDouble(AbstractMovement::getAmount)
                                                                .sum();
        return income - outcome;
    }


    public double getNetIncomeMonthComparison(long walletId, YearMonth compareThis, YearMonth compareToThis) {
        double netIncomeCompareThis =
            getNetIncome(walletId, startYearMonthToInstant(compareThis), endYearMonthToInstant(compareThis));
        double netIncomeCompareToThis =
            getNetIncome(walletId, startYearMonthToInstant(compareToThis), endYearMonthToInstant(compareToThis));
        return netIncomeCompareThis - netIncomeCompareToThis;
    }


    public double getMonthlyOutcomeMean(long walletId, YearMonth periodStart, YearMonth periodEnd) {
        double outcomes = getOutcomes(walletId, startYearMonthToInstant(periodStart), endYearMonthToInstant(periodEnd),
                                      false
        ).stream().mapToDouble(Outcome::getAmount).sum();
        return outcomes / (periodStart.until(periodEnd, ChronoUnit.MONTHS) + 1);
    }


    public double getMonthlyIncomeMean(long walletId, YearMonth periodStart, YearMonth periodEnd) {
        double incomes =
            getIncomes(walletId, startYearMonthToInstant(periodStart), endYearMonthToInstant(periodEnd), false).stream()
                                                                                                               .mapToDouble(
                                                                                                                   Income::getAmount)
                                                                                                               .sum();
        return incomes / (periodStart.until(periodEnd, ChronoUnit.MONTHS) + 1);
    }


    public Collection<CategoryStatistic> getOutcomeCategoriesMonthlyMean(long walletId, YearMonth periodStart,
                                                                         YearMonth periodEnd) {
        return getOutcomeCategoriesStatistic(
            walletId, startYearMonthToInstant(periodStart), endYearMonthToInstant(periodEnd), categories -> {
                Collection<CategoryStatistic> stats = new HashSet<>();
                categories.forEach(category -> {
                    stats.add(new CategoryStatistic(category.getId(), category.getName(),
                                                    getCategoryTotalOutcome(category) /
                                                        (periodStart.until(periodEnd, ChronoUnit.MONTHS) + 1)
                    ));
                });
                return stats;
            });
    }


    public Collection<CategoryStatistic> getOutcomeCategoriesTotal(long walletId, YearMonth periodStart,
                                                                   YearMonth periodEnd) {
        return getOutcomeCategoriesStatistic(
            walletId, startYearMonthToInstant(periodStart), endYearMonthToInstant(periodEnd), categories -> {
                Collection<CategoryStatistic> stats = new HashSet<>();
                categories.forEach(category -> {
                    stats.add(
                        new CategoryStatistic(category.getId(), category.getName(), getCategoryTotalOutcome(category)));
                });
                return stats;
            });
    }


    private Instant startYearMonthToInstant(YearMonth toConvert) {
        return toConvert.atDay(1).atStartOfDay().toInstant(ZoneOffset.UTC);
    }


    private Instant endYearMonthToInstant(YearMonth toConvert) {
        return toConvert.atDay(31).atStartOfDay().toInstant(ZoneOffset.UTC);
    }


    private Collection<CategoryStatistic> getOutcomeCategoriesStatistic(long walletId, Instant periodStart,
                                                                        Instant periodEnd,
                                                                        Function<Collection<Category>,
                                                                                    Collection<CategoryStatistic>> toApply) {
        Collection<Category> categories =
            getOutcomes(walletId, periodStart, periodEnd, false).stream().map(Outcome::getCategory)
                                                                .collect(Collectors.toSet());
        return toApply.apply(categories);
    }


    private double getCategoryTotalOutcome(Category category) {
        return category.getOutcomes().stream().mapToDouble(Outcome::getAmount).sum();
    }
}
