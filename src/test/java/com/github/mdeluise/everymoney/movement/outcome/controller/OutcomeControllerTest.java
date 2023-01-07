package com.github.mdeluise.everymoney.movement.outcome.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mdeluise.everymoney.ApplicationConfig;
import com.github.mdeluise.everymoney.TestEnvironment;
import com.github.mdeluise.everymoney.authentication.UserRepository;
import com.github.mdeluise.everymoney.authentication.UserService;
import com.github.mdeluise.everymoney.authorization.permission.PermissionRepository;
import com.github.mdeluise.everymoney.authorization.permission.PermissionService;
import com.github.mdeluise.everymoney.category.Category;
import com.github.mdeluise.everymoney.category.CategoryService;
import com.github.mdeluise.everymoney.category.subcategory.SubCategoryService;
import com.github.mdeluise.everymoney.exception.EntityNotFoundException;
import com.github.mdeluise.everymoney.movements.outcome.Outcome;
import com.github.mdeluise.everymoney.movements.outcome.OutcomeController;
import com.github.mdeluise.everymoney.movements.outcome.OutcomeDTO;
import com.github.mdeluise.everymoney.movements.outcome.OutcomeService;
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


@WebMvcTest(OutcomeController.class)
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
        PermissionService.class
    }
)
@WithMockUser(roles = "ADMIN")
public class OutcomeControllerTest {
    @MockBean
    OutcomeService outcomeService;
    @MockBean
    WalletRepository walletRepository;
    @MockBean
    WalletService walletService;
    @MockBean
    CategoryService categoryService;
    @MockBean
    SubCategoryService subCategoryService;
    @MockBean
    UserRepository userRepository;
    @MockBean
    PermissionRepository permissionRepository;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;


    @Test
    void whenGetOutcomes_ShouldReturnOutcomes() throws Exception {
        Outcome outcome1ToReturn = new Outcome();
        outcome1ToReturn.setDescription("outcome0");
        outcome1ToReturn.setId(0L);
        Outcome outcome2ToReturn = new Outcome();
        outcome2ToReturn.setDescription("outcome1");
        outcome2ToReturn.setId(0L);
        Mockito.when(outcomeService.getAll()).thenReturn(List.of(outcome1ToReturn, outcome2ToReturn));

        mockMvc.perform(MockMvcRequestBuilders.get("/outcome")).andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
               .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
    }


    @Test
    void whenGetOutcome_ShouldReturnOutcome() throws Exception {
        Outcome outcomeToReturn = new Outcome();
        outcomeToReturn.setDescription("outcome0");
        outcomeToReturn.setId(0L);
        Mockito.when(outcomeService.get(0L)).thenReturn(outcomeToReturn);

        mockMvc.perform(MockMvcRequestBuilders.get("/outcome/0")).andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("outcome0"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(0));
    }


    @Test
    void whenGetNotExistingOutcome_shouldError() throws Exception {
        Mockito.when(outcomeService.get(0L)).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/outcome/0"))
               .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }


    @Test
    void whenDeleteOutcome_shouldReturnOk() throws Exception {
        Mockito.doNothing().when(outcomeService).remove(0L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/outcome/0")).andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    void whenDeleteNonExistingOutcome_shouldError() throws Exception {
        Mockito.doThrow(EntityNotFoundException.class).when(outcomeService).remove(0L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/outcome/0"))
               .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }


    @Test
    void whenUpdateOutcome_shouldReturnUpdated() throws Exception {
        Outcome updated = new Outcome();
        updated.setId(0L);
        updated.setDescription("outcome1");
        Category linkedCategory = new Category();
        linkedCategory.setName("category0");
        updated.setCategory(linkedCategory);
        Wallet linkedWallet = new Wallet();
        linkedWallet.setId(0L);
        linkedWallet.setName("wallet0");
        updated.setWallet(linkedWallet);
        Mockito.when(outcomeService.update(0L, updated)).thenReturn(updated);
        Mockito.when(walletService.get(0L)).thenReturn(linkedWallet);

        mockMvc.perform(MockMvcRequestBuilders.put("/outcome/0").content(objectMapper.writeValueAsString(new OutcomeDTO(updated)))
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("outcome1"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(0));
    }


    @Test
    void whenUpdateNonExistingOutcome_shouldError() throws Exception {
        Outcome updated = new Outcome();
        updated.setId(0L);
        updated.setDescription("outcome1");
        Category linkedCategory = new Category();
        linkedCategory.setName("category0");
        updated.setCategory(linkedCategory);
        Wallet linkedWallet = new Wallet();
        linkedWallet.setId(0L);
        linkedWallet.setName("wallet0");
        updated.setWallet(linkedWallet);
        Mockito.doThrow(EntityNotFoundException.class).when(outcomeService).update(0L, updated);
        Mockito.when(walletService.get(0L)).thenReturn(linkedWallet);

        mockMvc.perform(MockMvcRequestBuilders.put("/outcome/0").content(objectMapper.writeValueAsString(new OutcomeDTO(updated)))
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }


    @Test
    void whenCreateOutcome_shouldReturnOutcome() throws Exception {
        Outcome created = new Outcome();
        created.setId(0L);
        created.setDescription("outcome1");
        Category linkedCategory = new Category();
        linkedCategory.setName("category0");
        created.setCategory(linkedCategory);
        Wallet linkedWallet = new Wallet();
        linkedWallet.setId(0L);
        linkedWallet.setName("wallet0");
        created.setWallet(linkedWallet);
        Mockito.when(outcomeService.save(created)).thenReturn(created);
        Mockito.when(walletService.get(0L)).thenReturn(linkedWallet);

        mockMvc.perform(MockMvcRequestBuilders.post("/outcome").content(objectMapper.writeValueAsString(new OutcomeDTO(created)))
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("outcome1"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(0));
    }
}
