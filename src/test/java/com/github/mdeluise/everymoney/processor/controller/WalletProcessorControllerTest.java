package com.github.mdeluise.everymoney.processor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mdeluise.everymoney.TestEnvironment;
import com.github.mdeluise.everymoney.category.CategoryService;
import com.github.mdeluise.everymoney.category.subcategory.SubCategoryService;
import com.github.mdeluise.everymoney.exception.EntityNotFoundException;
import com.github.mdeluise.everymoney.movements.income.Income;
import com.github.mdeluise.everymoney.movements.income.IncomeDTOConverter;
import com.github.mdeluise.everymoney.movements.outcome.Outcome;
import com.github.mdeluise.everymoney.movements.outcome.OutcomeDTOConverter;
import com.github.mdeluise.everymoney.processor.CategoryStatistic;
import com.github.mdeluise.everymoney.processor.WalletProcessor;
import com.github.mdeluise.everymoney.processor.WalletProcessorController;
import com.github.mdeluise.everymoney.security.ApplicationSecurityConfig;
import com.github.mdeluise.everymoney.security.jwt.JwtTokenFilter;
import com.github.mdeluise.everymoney.security.jwt.JwtTokenUtil;
import com.github.mdeluise.everymoney.security.jwt.JwtWebUtil;
import com.github.mdeluise.everymoney.wallet.Wallet;
import com.github.mdeluise.everymoney.wallet.WalletService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Instant;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Set;

@WebMvcTest(WalletProcessorController.class)
@Import(
    {
        JwtTokenFilter.class,
        JwtTokenUtil.class,
        JwtWebUtil.class,
        TestEnvironment.class,
        ApplicationSecurityConfig.class,
        OutcomeDTOConverter.class,
        ModelMapper.class,
        IncomeDTOConverter.class,
        OutcomeDTOConverter.class
    }
)
@WithMockUser(roles = "ADMIN")
public class WalletProcessorControllerTest {
    @MockBean
    WalletProcessor walletProcessor;
    @MockBean
    WalletService walletService;
    @MockBean
    CategoryService categoryService;
    @MockBean
    SubCategoryService subCategoryService;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;


    @Test
    void whenGetBalance_thenReturnBalance() throws Exception {
        Mockito.when(walletProcessor.getBalance(0L)).thenReturn(42.);

        mockMvc.perform(MockMvcRequestBuilders.get("/stats/balance/0")).andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$").value(42));
    }


    @Test
    void whenGetBalanceNonExistingWallet_thenError() throws Exception {
        Mockito.doThrow(EntityNotFoundException.class).when(walletProcessor).getBalance(0L);

        mockMvc.perform(MockMvcRequestBuilders.get("/stats/balance/0"))
               .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }


    @Test
    void whenGetIncomes_thenReturnIncomes() throws Exception {
        Instant instant1 = Instant.now();
        Instant instant2 = instant1.plus(10, ChronoUnit.DAYS);
        Wallet linkedWallet = new Wallet();
        linkedWallet.setId(0L);

        Mockito.when(walletProcessor.getIncomes(0L, instant1, instant2, true)).thenReturn(createIncomes(linkedWallet));
        Mockito.when(walletService.get(0L)).thenReturn(linkedWallet);

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/stats/income/0/%s/%s", instant1, instant2)))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
               .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
    }


    private Set<Income> createIncomes(Wallet linkedWallet) {
        Income income1 = new Income();
        income1.setId(0L);
        income1.setDescription("income1");
        income1.setWallet(linkedWallet);
        Income income2 = new Income();
        income2.setId(1L);
        income2.setDescription("income2");
        income2.setWallet(linkedWallet);
        return Set.of(income1, income2);
    }


    @Test
    void whenGetIncomesNotExistingWallet_thenError() throws Exception {
        Instant instant1 = Instant.now();
        Instant instant2 = instant1.plus(10, ChronoUnit.DAYS);
        Wallet linekdwallet = new Wallet();
        linekdwallet.setId(0L);

        Mockito.doThrow(EntityNotFoundException.class).when(walletProcessor).getIncomes(0L, instant1, instant2, true);
        Mockito.when(walletService.get(0L)).thenReturn(linekdwallet);

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/stats/income/0/%s/%s", instant1, instant2)))
               .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }


    @Test
    void whenGetOutcomes_thenReturnOutcomes() throws Exception {
        Instant instant1 = Instant.now();
        Instant instant2 = instant1.plus(10, ChronoUnit.DAYS);
        Wallet linekdwallet = new Wallet();
        linekdwallet.setId(0L);

        Mockito.when(walletProcessor.getOutcomes(0L, instant1, instant2, true)).thenReturn(createOutcome(linekdwallet));
        Mockito.when(walletService.get(0L)).thenReturn(linekdwallet);

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/stats/outcome/0/%s/%s", instant1, instant2)))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
               .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
    }


    @Test
    void whenGetOutcomesNotExistingWallet_thenError() throws Exception {
        Instant instant1 = Instant.now();
        Instant instant2 = instant1.plus(10, ChronoUnit.DAYS);
        Wallet linekdwallet = new Wallet();
        linekdwallet.setId(0L);

        Mockito.doThrow(EntityNotFoundException.class).when(walletProcessor).getOutcomes(0L, instant1, instant2, true);
        Mockito.when(walletService.get(0L)).thenReturn(linekdwallet);

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/stats/outcome/0/%s/%s", instant1, instant2)))
               .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }


    private Set<Outcome> createOutcome(Wallet linkedWallet) {
        Outcome outcome1 = new Outcome();
        outcome1.setId(0L);
        outcome1.setDescription("income1");
        outcome1.setWallet(linkedWallet);
        Outcome outcome2 = new Outcome();
        outcome2.setId(1L);
        outcome2.setDescription("income2");
        outcome2.setWallet(linkedWallet);
        return Set.of(outcome1, outcome2);
    }


    @Test
    void whenGetNetIncome_thenReturnNetIncome() throws Exception {
        Instant instant1 = Instant.now();
        Instant instant2 = instant1.plus(10, ChronoUnit.DAYS);
        Mockito.when(walletProcessor.getNetIncome(0L, instant1, instant2)).thenReturn(42.);

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/stats/net-income/0/%s/%s", instant1, instant2)))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$").value(42));
    }


    @Test
    void whenGetNetIncomeNonExistingWallet_thenError() throws Exception {
        Instant instant1 = Instant.now();
        Instant instant2 = instant1.plus(10, ChronoUnit.DAYS);
        Mockito.doThrow(EntityNotFoundException.class).when(walletProcessor).getNetIncome(0L, instant1, instant2);

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/stats/net-income/0/%s/%s", instant1, instant2)))
               .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }


    @Test
    void whenGetCompareMonthlyNetIncome_thenReturnCompareMonthlyNetIncome() throws Exception {
        YearMonth yearMonth1 = YearMonth.now();
        YearMonth yearMonth2 = yearMonth1.plusMonths(3);
        Mockito.when(walletProcessor.getNetIncomeMonthComparison(0L, yearMonth1, yearMonth2)).thenReturn(42.);

        mockMvc.perform(
                   MockMvcRequestBuilders.get(String.format("/stats/compare/net-income/0/%s/%s", yearMonth1,
                                                            yearMonth2)))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$").value(42));
    }


    @Test
    void whenGetCompareMonthlyNetIncomeNonExistingWallet_thenError() throws Exception {
        YearMonth yearMonth1 = YearMonth.now();
        YearMonth yearMonth2 = yearMonth1.plusMonths(3);
        Mockito.doThrow(EntityNotFoundException.class).when(walletProcessor)
               .getNetIncomeMonthComparison(0L, yearMonth1, yearMonth2);

        mockMvc.perform(
                   MockMvcRequestBuilders.get(String.format("/stats/compare/net-income/0/%s/%s", yearMonth1,
                                                            yearMonth2)))
               .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }


    @Test
    void whenGetMonthlyOutcomesMean_thenReturnMonthlyOutcomesMean() throws Exception {
        YearMonth yearMonth1 = YearMonth.now();
        YearMonth yearMonth2 = yearMonth1.plusMonths(3);
        Mockito.when(walletProcessor.getMonthlyOutcomeMean(0L, yearMonth1, yearMonth2)).thenReturn(42.);

        mockMvc.perform(
                   MockMvcRequestBuilders.get(String.format("/stats/outcome/mean/0/%s/%s", yearMonth1, yearMonth2)))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$").value(42));
    }


    @Test
    void whenGetMonthlyOutcomesMeanNonExistingWallet_thenError() throws Exception {
        YearMonth yearMonth1 = YearMonth.now();
        YearMonth yearMonth2 = yearMonth1.plusMonths(3);
        Mockito.doThrow(EntityNotFoundException.class).when(walletProcessor)
               .getMonthlyOutcomeMean(0L, yearMonth1, yearMonth2);

        mockMvc.perform(
                   MockMvcRequestBuilders.get(String.format("/stats/outcome/mean/0/%s/%s", yearMonth1, yearMonth2)))
               .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }


    @Test
    void whenGetMonthlyIncomesMean_thenReturnMonthlyIncomesMean() throws Exception {
        YearMonth yearMonth1 = YearMonth.now();
        YearMonth yearMonth2 = yearMonth1.plusMonths(3);
        Mockito.when(walletProcessor.getMonthlyIncomeMean(0L, yearMonth1, yearMonth2)).thenReturn(42.);

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/stats/income/mean/0/%s/%s", yearMonth1, yearMonth2)))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$").value(42));
    }


    @Test
    void whenGetMonthlyIncomesMeanNonExistingWallet_thenError() throws Exception {
        YearMonth yearMonth1 = YearMonth.now();
        YearMonth yearMonth2 = yearMonth1.plusMonths(3);
        Mockito.doThrow(EntityNotFoundException.class).when(walletProcessor)
               .getMonthlyIncomeMean(0L, yearMonth1, yearMonth2);

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/stats/income/mean/0/%s/%s", yearMonth1, yearMonth2)))
               .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }


    @Test
    void whenGetMonthlyOutcomeCategoriesMean_thenReturnMonthlyOutcomeCategoriesMean() throws Exception {
        YearMonth yearMonth1 = YearMonth.now();
        YearMonth yearMonth2 = yearMonth1.plusMonths(3);
        Collection<CategoryStatistic> categoriesStats = createCategoryStats();
        Mockito.when(walletProcessor.getOutcomeCategoriesMonthlyMean(0L, yearMonth1, yearMonth2))
               .thenReturn(categoriesStats);

        mockMvc.perform(
                   MockMvcRequestBuilders.get(String.format("/stats/categories/mean/0/%s/%s", yearMonth1, yearMonth2)))
               .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    void whenGetMonthlyOutcomesCategoriesMeanNonExistingWallet_thenError() throws Exception {
        YearMonth yearMonth1 = YearMonth.now();
        YearMonth yearMonth2 = yearMonth1.plusMonths(3);
        Mockito.doThrow(EntityNotFoundException.class).when(walletProcessor)
               .getOutcomeCategoriesMonthlyMean(0L, yearMonth1, yearMonth2);

        mockMvc.perform(
                   MockMvcRequestBuilders.get(String.format("/stats/categories/mean/0/%s/%s", yearMonth1, yearMonth2)))
               .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }


    @Test
    void whenGetMonthlyOutcomesCategoriesTotal_thenReturnMonthlyOutcomesCategoriesTotal() throws Exception {
        YearMonth yearMonth1 = YearMonth.now();
        YearMonth yearMonth2 = yearMonth1.plusMonths(3);
        Collection<CategoryStatistic> categoriesStats = createCategoryStats();
        Mockito.when(walletProcessor.getOutcomeCategoriesTotal(0L, yearMonth1, yearMonth2)).thenReturn(categoriesStats);

        mockMvc.perform(
                   MockMvcRequestBuilders.get(String.format("/stats/categories/total/0/%s/%s", yearMonth1, yearMonth2)))
               .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    void whenGetMonthlyOutcomesCategoriesTotalNonExistingWallet_thenError() throws Exception {
        YearMonth yearMonth1 = YearMonth.now();
        YearMonth yearMonth2 = yearMonth1.plusMonths(3);
        Mockito.doThrow(EntityNotFoundException.class).when(walletProcessor)
               .getOutcomeCategoriesTotal(0L, yearMonth1, yearMonth2);

        mockMvc.perform(
                   MockMvcRequestBuilders.get(String.format("/stats/categories/total/0/%s/%s", yearMonth1, yearMonth2)))
               .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }


    private Collection<CategoryStatistic> createCategoryStats() {
        return Set.of(new CategoryStatistic(0L, "category1", 0), new CategoryStatistic(1L, "category2", 1),
                      new CategoryStatistic(2L, "category3", 2), new CategoryStatistic(3L, "category4", 3)

        );
    }


}
