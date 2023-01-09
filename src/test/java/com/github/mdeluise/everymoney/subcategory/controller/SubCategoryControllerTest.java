package com.github.mdeluise.everymoney.subcategory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mdeluise.everymoney.TestEnvironment;
import com.github.mdeluise.everymoney.category.Category;
import com.github.mdeluise.everymoney.category.CategoryService;
import com.github.mdeluise.everymoney.category.subcategory.SubCategory;
import com.github.mdeluise.everymoney.category.subcategory.SubCategoryController;
import com.github.mdeluise.everymoney.category.subcategory.SubCategoryDTOConverter;
import com.github.mdeluise.everymoney.category.subcategory.SubCategoryRepository;
import com.github.mdeluise.everymoney.category.subcategory.SubCategoryService;
import com.github.mdeluise.everymoney.exception.EntityNotFoundException;
import com.github.mdeluise.everymoney.movements.outcome.OutcomeDTOConverter;
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

@WebMvcTest(SubCategoryController.class)
@Import(
    {
        JwtTokenFilter.class,
        JwtTokenUtil.class,
        JwtWebUtil.class,
        TestEnvironment.class,
        ApplicationSecurityConfig.class,
        OutcomeDTOConverter.class,
        SubCategoryDTOConverter.class,
        CategoryService.class,
        ModelMapper.class,
        SubCategoryDTOConverter.class
    }
)
@WithMockUser(roles = "ADMIN")
public class SubCategoryControllerTest {
    @MockBean
    CategoryService categoryService;
    @MockBean
    OutcomeService outcomeService;
    @MockBean
    WalletService walletService;
    @MockBean
    SubCategoryRepository subCategoryRepository;
    @MockBean
    SubCategoryService subCategoryService;
    @Autowired
    SubCategoryDTOConverter subCategoryDTOConverter;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;


    @Test
    void whenGetSubCategories_ShouldReturnSubCategories() throws Exception {
        Category parentCategory = new Category();
        parentCategory.setName("category0");
        parentCategory.setId(0L);
        Mockito.when(categoryService.get(0L)).thenReturn(parentCategory);
        SubCategory subCategory1ToReturn = new SubCategory();
        subCategory1ToReturn.setName("subCategory0");
        subCategory1ToReturn.setId(0L);
        subCategory1ToReturn.setCategory(parentCategory);
        SubCategory subCategory2ToReturn = new SubCategory();
        subCategory2ToReturn.setName("subCategory1");
        subCategory2ToReturn.setId(0L);
        subCategory2ToReturn.setCategory(parentCategory);
        Mockito.when(subCategoryService.getAll()).thenReturn(List.of(subCategory1ToReturn, subCategory2ToReturn));

        mockMvc.perform(MockMvcRequestBuilders.get("/sub_category")).andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
               .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
    }


    @Test
    void whenGetSubCategory_ShouldReturnSubCategory() throws Exception {
        Category parentCategory = new Category();
        parentCategory.setName("category0");
        parentCategory.setId(0L);
        Mockito.when(categoryService.get(0L)).thenReturn(parentCategory);
        SubCategory subCategoryToReturn = new SubCategory();
        subCategoryToReturn.setName("subCategory0");
        subCategoryToReturn.setId(0L);
        subCategoryToReturn.setCategory(parentCategory);
        Mockito.when(subCategoryService.get(0L)).thenReturn(subCategoryToReturn);

        mockMvc.perform(MockMvcRequestBuilders.get("/sub_category/0")).andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("subCategory0"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(0));
    }


    @Test
    void whenGetNotExistingSubCategory_shouldError() throws Exception {
        Mockito.when(subCategoryService.get(0L)).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/sub_category/0"))
               .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }


    @Test
    void whenDeleteSubCategory_shouldReturnOk() throws Exception {
        Mockito.doNothing().when(subCategoryService).remove(0L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/sub_category/0"))
               .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    void whenDeleteNonExistingSubCategory_shouldError() throws Exception {
        Mockito.doThrow(EntityNotFoundException.class).when(subCategoryService).remove(0L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/sub_category/0"))
               .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }


    @Test
    void whenUpdateSubCategory_shouldReturnUpdated() throws Exception {
        Category parentCategory = new Category();
        parentCategory.setName("category0");
        parentCategory.setId(0L);
        Mockito.when(categoryService.get(0L)).thenReturn(parentCategory);
        SubCategory updated = new SubCategory();
        updated.setId(0L);
        updated.setName("subCategory1");
        updated.setDescription("description1");
        updated.setCategory(parentCategory);
        Mockito.when(subCategoryService.update(0L, updated)).thenReturn(updated);

        mockMvc.perform(MockMvcRequestBuilders.put("/sub_category/0").content(
                                                  objectMapper.writeValueAsString(subCategoryDTOConverter.convertToDTO(updated)))
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("subCategory1"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(0))
               .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("description1"));
    }


    @Test
    void whenUpdateNonExistingSubCategory_shouldError() throws Exception {
        Category parentCategory = new Category();
        parentCategory.setName("category0");
        parentCategory.setId(0L);
        Mockito.when(categoryService.get(0L)).thenReturn(parentCategory);
        SubCategory updated = new SubCategory();
        updated.setName("subCategory1");
        updated.setDescription("description1");
        updated.setCategory(parentCategory);
        Mockito.doThrow(EntityNotFoundException.class).when(subCategoryService).update(0L, updated);

        mockMvc.perform(MockMvcRequestBuilders.put("/sub_category/0").content(
                                                  objectMapper.writeValueAsString(subCategoryDTOConverter.convertToDTO(updated)))
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }


    @Test
    void whenCreateSubCategory_shouldReturnSubCategory() throws Exception {
        Category parentCategory = new Category();
        parentCategory.setName("category0");
        parentCategory.setId(0L);
        Mockito.when(categoryService.get(0L)).thenReturn(parentCategory);
        SubCategory created = new SubCategory();
        created.setId(0L);
        created.setName("subCategory1");
        created.setDescription("description1");
        created.setCategory(parentCategory);
        Mockito.when(subCategoryService.save(created)).thenReturn(created);

        mockMvc.perform(MockMvcRequestBuilders.post("/sub_category").content(
                                                  objectMapper.writeValueAsString(subCategoryDTOConverter.convertToDTO(created)))
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("subCategory1"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(0))
               .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("description1"));
    }
}
