package com.github.mdeluise.everymoney.wallet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mdeluise.everymoney.TestEnvironment;
import com.github.mdeluise.everymoney.category.CategoryService;
import com.github.mdeluise.everymoney.category.subcategory.SubCategoryService;
import com.github.mdeluise.everymoney.exception.EntityNotFoundException;
import com.github.mdeluise.everymoney.movements.MovementDTOConverter;
import com.github.mdeluise.everymoney.movements.income.IncomeDTOConverter;
import com.github.mdeluise.everymoney.movements.outcome.OutcomeDTOConverter;
import com.github.mdeluise.everymoney.security.ApplicationSecurityConfig;
import com.github.mdeluise.everymoney.security.jwt.JwtTokenFilter;
import com.github.mdeluise.everymoney.security.jwt.JwtTokenUtil;
import com.github.mdeluise.everymoney.security.jwt.JwtWebUtil;
import com.github.mdeluise.everymoney.wallet.Wallet;
import com.github.mdeluise.everymoney.wallet.WalletController;
import com.github.mdeluise.everymoney.wallet.WalletDTOConverter;
import com.github.mdeluise.everymoney.wallet.WalletService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
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


@WebMvcTest(WalletController.class)
@Import(
    {
        JwtTokenFilter.class,
        JwtTokenUtil.class,
        JwtWebUtil.class,
        TestEnvironment.class,
        ApplicationSecurityConfig.class,
        WalletDTOConverter.class,
        ModelMapper.class,
        MovementDTOConverter.class,
        IncomeDTOConverter.class,
        OutcomeDTOConverter.class
    }
)
@WithMockUser(roles = "ADMIN")
public class WalletControllerTest {
    @MockBean
    WalletService walletService;
    @MockBean
    CategoryService categoryService;
    @MockBean
    SubCategoryService subCategoryService;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    WalletDTOConverter walletDTOConverter;
    @Autowired
    private MockMvc mockMvc;


    @Test
    void whenGetWallets_ShouldReturnWallets() throws Exception {
        Wallet wallet1ToReturn = new Wallet();
        wallet1ToReturn.setName("wallet0");
        wallet1ToReturn.setId(0L);
        Wallet wallet2ToReturn = new Wallet();
        wallet2ToReturn.setName("wallet1");
        wallet2ToReturn.setId(0L);
        Mockito.when(walletService.getAll()).thenReturn(List.of(wallet1ToReturn, wallet2ToReturn));

        mockMvc.perform(MockMvcRequestBuilders.get("/wallet")).andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
               .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
    }


    @Test
    void whenGetWallet_ShouldReturnWallet() throws Exception {
        Wallet walletToReturn = new Wallet();
        walletToReturn.setName("wallet0");
        walletToReturn.setId(0L);
        Mockito.when(walletService.get(0L)).thenReturn(walletToReturn);

        mockMvc.perform(MockMvcRequestBuilders.get("/wallet/0")).andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("wallet0"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(0));
    }


    @Test
    void whenGetNotExistingWallet_shouldError() throws Exception {
        Mockito.when(walletService.get(0L)).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/wallet/0"))
               .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }


    @Test
    void whenDeleteWallet_shouldReturnOk() throws Exception {
        Mockito.doNothing().when(walletService).remove(0L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/wallet/0")).andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    void whenDeleteNonExistingWallet_shouldError() throws Exception {
        Mockito.doThrow(EntityNotFoundException.class).when(walletService).remove(0L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/wallet/0"))
               .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }


    @Test
    void whenUpdateWallet_shouldReturnUpdated() throws Exception {
        Wallet updated = new Wallet();
        updated.setId(0L);
        updated.setName("wallet1");
        updated.setDescription("description1");
        Mockito.when(walletService.update(0L, updated)).thenReturn(updated);

        mockMvc.perform(MockMvcRequestBuilders.put("/wallet/0").content(
                                                  objectMapper.writeValueAsString(walletDTOConverter.convertToDTO(updated)))
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("wallet1"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(0))
               .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("description1"));
    }


    @Test
    void whenUpdateNonExistingWallet_shouldError() throws Exception {
        Wallet updated = new Wallet();
        updated.setName("wallet1");
        updated.setDescription("description1");
        Mockito.doThrow(EntityNotFoundException.class).when(walletService).update(0L, updated);

        mockMvc.perform(MockMvcRequestBuilders.put("/wallet/0").content(
                                                  objectMapper.writeValueAsString(walletDTOConverter.convertToDTO(updated)))
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }


    @Test
    void whenCreateWallet_shouldReturnWallet() throws Exception {
        Wallet created = new Wallet();
        created.setId(0L);
        created.setName("wallet1");
        created.setDescription("description1");
        Mockito.when(walletService.save(created)).thenReturn(created);

        mockMvc.perform(MockMvcRequestBuilders.post("/wallet").content(
                                                  objectMapper.writeValueAsString(walletDTOConverter.convertToDTO(created)))
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("wallet1"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(0))
               .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("description1"));
    }
}
