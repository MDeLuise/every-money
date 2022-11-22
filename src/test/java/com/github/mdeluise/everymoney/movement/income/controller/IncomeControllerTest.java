package com.github.mdeluise.everymoney.movement.income.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mdeluise.everymoney.ApplicationConfig;
import com.github.mdeluise.everymoney.TestEnvironment;
import com.github.mdeluise.everymoney.authentication.UserRepository;
import com.github.mdeluise.everymoney.authentication.UserService;
import com.github.mdeluise.everymoney.authorization.permission.PermissionRepository;
import com.github.mdeluise.everymoney.authorization.permission.PermissionService;
import com.github.mdeluise.everymoney.exception.EntityNotFoundException;
import com.github.mdeluise.everymoney.movements.income.Income;
import com.github.mdeluise.everymoney.movements.income.IncomeController;
import com.github.mdeluise.everymoney.movements.income.IncomeDTOConverter;
import com.github.mdeluise.everymoney.movements.income.IncomeService;
import com.github.mdeluise.everymoney.security.ApplicationSecurityConfig;
import com.github.mdeluise.everymoney.security.jwt.JwtTokenFilter;
import com.github.mdeluise.everymoney.security.jwt.JwtTokenUtil;
import com.github.mdeluise.everymoney.security.jwt.JwtWebUtil;
import com.github.mdeluise.everymoney.wallet.Wallet;
import com.github.mdeluise.everymoney.wallet.WalletRepository;
import com.github.mdeluise.everymoney.wallet.WalletService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;


@WebMvcTest(IncomeController.class)
@Import(
    {
        JwtTokenFilter.class,
        JwtTokenUtil.class,
        JwtWebUtil.class,
        TestEnvironment.class,
        ApplicationSecurityConfig.class,
        ApplicationConfig.class,
        WalletService.class,
        UserService.class,
        PermissionService.class,
        IncomeDTOConverter.class
    }
)
@WithMockUser(roles = "ADMIN")
public class IncomeControllerTest {
    @MockBean
    IncomeService incomeService;
    @MockBean
    WalletRepository walletRepository;
    @MockBean
    WalletService walletService;
    @MockBean
    UserRepository userRepository;
    @MockBean
    PermissionRepository permissionRepository;
    @Autowired
    IncomeDTOConverter incomeDTOConverter;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;


    @Test
    void whenGetIncomes_ShouldReturnIncomes() throws Exception {
        Income income1ToReturn = new Income();
        income1ToReturn.setDescription("income0");
        income1ToReturn.setId(0L);
        Income income2ToReturn = new Income();
        income2ToReturn.setDescription("income1");
        income2ToReturn.setId(0L);
        Mockito.when(incomeService.getAll()).thenReturn(List.of(income1ToReturn, income2ToReturn));

        mockMvc.perform(MockMvcRequestBuilders.get("/income")).andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
               .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
    }


    @Test
    void whenGetIncome_ShouldReturnIncome() throws Exception {
        Income incomeToReturn = new Income();
        incomeToReturn.setDescription("income0");
        incomeToReturn.setId(0L);
        Mockito.when(incomeService.get(0L)).thenReturn(incomeToReturn);

        mockMvc.perform(MockMvcRequestBuilders.get("/income/0")).andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("income0"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(0));
    }


    @Test
    void whenGetNotExistingIncome_shouldError() throws Exception {
        Mockito.when(incomeService.get(0L)).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/income/0"))
               .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }


    @Test
    void whenDeleteIncome_shouldReturnOk() throws Exception {
        Mockito.doNothing().when(incomeService).remove(0L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/income/0")).andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    void whenDeleteNonExistingIncome_shouldError() throws Exception {
        Mockito.doThrow(EntityNotFoundException.class).when(incomeService).remove(0L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/income/0"))
               .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }


    @Test
    void whenUpdateIncome_shouldReturnUpdated() throws Exception {
        Income updated = new Income();
        updated.setId(0L);
        updated.setDescription("income1");
        Wallet linkedWallet = new Wallet();
        linkedWallet.setName("wallet0");
        linkedWallet.setId(0L);
        updated.setWallet(linkedWallet);
        Mockito.when(incomeService.update(0L, updated)).thenReturn(updated);
        Mockito.when(walletService.get(0L)).thenReturn(linkedWallet);

        mockMvc.perform(MockMvcRequestBuilders.put("/income/0").content(
                                                  objectMapper.writeValueAsString(incomeDTOConverter.convertToDTO(updated)))
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("income1"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(0));
    }


    @Test
    void whenUpdateNonExistingIncome_shouldError() throws Exception {
        Income updated = new Income();
        updated.setId(0L);
        updated.setDescription("income1");
        Wallet linkedWallet = new Wallet();
        linkedWallet.setName("wallet0");
        linkedWallet.setId(0L);
        updated.setWallet(linkedWallet);
        Mockito.doThrow(EntityNotFoundException.class).when(incomeService).update(0L, updated);
        Mockito.when(walletService.get(0L)).thenReturn(linkedWallet);

        mockMvc.perform(MockMvcRequestBuilders.put("/income/0").content(
                                                  objectMapper.writeValueAsString(incomeDTOConverter.convertToDTO(updated)))
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }


    @Test
    void whenCreateIncome_shouldReturnIncome() throws Exception {
        Income created = new Income();
        created.setId(0L);
        created.setDescription("income1");
        Wallet linkedWallet = new Wallet();
        linkedWallet.setId(0L);
        linkedWallet.setName("wallet0");
        created.setWallet(linkedWallet);
        Mockito.when(incomeService.save(created)).thenReturn(created);
        Mockito.when(walletService.get(0L)).thenReturn(linkedWallet);

        mockMvc.perform(MockMvcRequestBuilders.post("/income").content(
                                                  objectMapper.writeValueAsString(incomeDTOConverter.convertToDTO(created)))
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("income1"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(0));
    }
}
