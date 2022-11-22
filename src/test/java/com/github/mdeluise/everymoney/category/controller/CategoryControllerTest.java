package com.github.mdeluise.everymoney.category.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mdeluise.everymoney.TestEnvironment;
import com.github.mdeluise.everymoney.category.Category;
import com.github.mdeluise.everymoney.category.CategoryController;
import com.github.mdeluise.everymoney.category.CategoryDTOConverter;
import com.github.mdeluise.everymoney.category.CategoryService;
import com.github.mdeluise.everymoney.category.subcategory.SubCategoryDTOConverter;
import com.github.mdeluise.everymoney.category.subcategory.SubCategoryService;
import com.github.mdeluise.everymoney.exception.EntityNotFoundException;
import com.github.mdeluise.everymoney.movements.outcome.OutcomeDTOConverter;
import com.github.mdeluise.everymoney.movements.outcome.OutcomeRepository;
import com.github.mdeluise.everymoney.movements.outcome.OutcomeService;
import com.github.mdeluise.everymoney.security.ApplicationSecurityConfig;
import com.github.mdeluise.everymoney.security.jwt.JwtTokenFilter;
import com.github.mdeluise.everymoney.security.jwt.JwtTokenUtil;
import com.github.mdeluise.everymoney.security.jwt.JwtWebUtil;
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


@WebMvcTest(CategoryController.class)
@Import(
    {
        JwtTokenFilter.class,
        JwtTokenUtil.class,
        JwtWebUtil.class,
        TestEnvironment.class,
        ApplicationSecurityConfig.class,
        CategoryDTOConverter.class,
        SubCategoryDTOConverter.class,
        OutcomeDTOConverter.class,
        ModelMapper.class
    }
)
@WithMockUser(roles = "ADMIN")
public class CategoryControllerTest {
    @MockBean
    CategoryService categoryService;
    @MockBean
    OutcomeService outcomeService;
    @MockBean
    OutcomeRepository outcomeRepository;
    @MockBean
    WalletService walletService;
    @MockBean
    SubCategoryService subCategoryService;
    @Autowired
    CategoryDTOConverter categoryDTOConverter;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;


    @Test
    void whenGetCategories_ShouldReturnCategories() throws Exception {
        Category category1ToReturn = new Category();
        category1ToReturn.setName("category0");
        category1ToReturn.setId(0L);
        Category category2ToReturn = new Category();
        category2ToReturn.setName("category1");
        category2ToReturn.setId(0L);
        Mockito.when(categoryService.getAll()).thenReturn(List.of(category1ToReturn, category2ToReturn));

        mockMvc.perform(MockMvcRequestBuilders.get("/category")).andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
               .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
    }


    @Test
    void whenGetCategory_ShouldReturnCategory() throws Exception {
        Category categoryToReturn = new Category();
        categoryToReturn.setName("category0");
        categoryToReturn.setId(0L);
        Mockito.when(categoryService.get(0L)).thenReturn(categoryToReturn);

        mockMvc.perform(MockMvcRequestBuilders.get("/category/0")).andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("category0"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(0));
    }


    @Test
    void whenGetNotExistingCategory_shouldError() throws Exception {
        Mockito.when(categoryService.get(0L)).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/category/0"))
               .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }


    @Test
    void whenDeleteCategory_shouldReturnOk() throws Exception {
        Mockito.doNothing().when(categoryService).remove(0L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/category/0")).andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    void whenDeleteNonExistingCategory_shouldError() throws Exception {
        Mockito.doThrow(EntityNotFoundException.class).when(categoryService).remove(0L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/category/0"))
               .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }


    @Test
    void whenUpdateCategory_shouldReturnUpdated() throws Exception {
        Category updated = new Category();
        updated.setId(0L);
        updated.setName("category1");
        updated.setDescription("description1");
        Mockito.when(categoryService.update(0L, updated)).thenReturn(updated);

        mockMvc.perform(MockMvcRequestBuilders.put("/category/0").content(
                                                  objectMapper.writeValueAsString(categoryDTOConverter.convertToDTO(updated)))
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("category1"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(0))
               .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("description1"));
    }


    @Test
    void whenUpdateNonExistingCategory_shouldError() throws Exception {
        Category updated = new Category();
        updated.setName("category1");
        updated.setDescription("description1");
        Mockito.doThrow(EntityNotFoundException.class).when(categoryService).update(0L, updated);

        mockMvc.perform(MockMvcRequestBuilders.put("/category/0").content(
                                                  objectMapper.writeValueAsString(categoryDTOConverter.convertToDTO(updated)))
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }


    @Test
    void whenCreateCategory_shouldReturnCategory() throws Exception {
        Category created = new Category();
        created.setId(0L);
        created.setName("category1");
        created.setDescription("description1");
        Mockito.when(categoryService.save(created)).thenReturn(created);

        mockMvc.perform(MockMvcRequestBuilders.post("/category").content(
                                                  objectMapper.writeValueAsString(categoryDTOConverter.convertToDTO(created)))
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("category1"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(0))
               .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("description1"));
    }
}
