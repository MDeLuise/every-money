package com.github.mdeluise.everymoney.subcategory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mdeluise.everymoney.TestEnvironment;
import com.github.mdeluise.everymoney.category.Category;
import com.github.mdeluise.everymoney.category.subcategory.SubCategory;
import com.github.mdeluise.everymoney.category.subcategory.SubCategoryController;
import com.github.mdeluise.everymoney.category.subcategory.SubCategoryService;
import com.github.mdeluise.everymoney.exception.EntityNotFoundException;
import com.github.mdeluise.everymoney.security.ApplicationSecurityConfig;
import com.github.mdeluise.everymoney.security.jwt.JwtTokenFilter;
import com.github.mdeluise.everymoney.security.jwt.JwtTokenUtil;
import com.github.mdeluise.everymoney.security.jwt.JwtWebUtil;
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

@WebMvcTest(SubCategoryController.class)
@Import(
    {
        JwtTokenFilter.class,
        JwtTokenUtil.class,
        JwtWebUtil.class,
        TestEnvironment.class,
        ApplicationSecurityConfig.class,
    }
)
@WithMockUser(roles = "ADMIN")
public class SubCategoryControllerTest {
    @MockBean
    SubCategoryService subSubCategoryService;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;


    @Test
    void whenGetSubCategories_ShouldReturnSubCategories() throws Exception {
        SubCategory subCategory1ToReturn = new SubCategory();
        subCategory1ToReturn.setName("subCategory0");
        subCategory1ToReturn.setId(0L);
        SubCategory subCategory2ToReturn = new SubCategory();
        subCategory2ToReturn.setName("subCategory1");
        subCategory2ToReturn.setId(0L);
        Mockito.when(subSubCategoryService.getAll()).thenReturn(List.of(subCategory1ToReturn, subCategory2ToReturn));

        mockMvc.perform(MockMvcRequestBuilders.get("/sub_category")).andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
               .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
    }


    @Test
    void whenGetSubCategory_ShouldReturnSubCategory() throws Exception {
        SubCategory subCategoryToReturn = new SubCategory();
        subCategoryToReturn.setName("subCategory0");
        subCategoryToReturn.setId(0L);
        Mockito.when(subSubCategoryService.get(0L)).thenReturn(subCategoryToReturn);

        mockMvc.perform(MockMvcRequestBuilders.get("/sub_category/0")).andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("subCategory0"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(0));
    }


    @Test
    void whenGetNotExistingSubCategory_shouldError() throws Exception {
        Mockito.when(subSubCategoryService.get(0L)).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/sub_category/0"))
               .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }


    @Test
    void whenDeleteSubCategory_shouldReturnOk() throws Exception {
        Mockito.doNothing().when(subSubCategoryService).remove(0L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/sub_category/0")).andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    void whenDeleteNonExistingSubCategory_shouldError() throws Exception {
        Mockito.doThrow(EntityNotFoundException.class).when(subSubCategoryService).remove(0L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/sub_category/0"))
               .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }


    @Test
    void whenUpdateSubCategory_shouldReturnUpdated() throws Exception {
        Category parentCategory = new Category();
        parentCategory.setName("category0");
        parentCategory.setId(0L);
        SubCategory updated = new SubCategory();
        updated.setId(0L);
        updated.setName("subCategory1");
        updated.setDescription("description1");
        updated.setCategory(parentCategory);
        Mockito.when(subSubCategoryService.update(0L, updated)).thenReturn(updated);

        mockMvc.perform(MockMvcRequestBuilders.put("/sub_category/0").content(objectMapper.writeValueAsString(updated))
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
        SubCategory updated = new SubCategory();
        updated.setName("subCategory1");
        updated.setDescription("description1");
        updated.setCategory(parentCategory);
        Mockito.doThrow(EntityNotFoundException.class).when(subSubCategoryService).update(0L, updated);

        mockMvc.perform(MockMvcRequestBuilders.put("/sub_category/0").content(objectMapper.writeValueAsString(updated))
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }


    @Test
    void whenCreateSubCategory_shouldReturnSubCategory() throws Exception {
        Category parentCategory = new Category();
        parentCategory.setName("category0");
        parentCategory.setId(0L);
        SubCategory created = new SubCategory();
        created.setId(0L);
        created.setName("subCategory1");
        created.setDescription("description1");
        created.setCategory(parentCategory);
        Mockito.when(subSubCategoryService.save(created)).thenReturn(created);

        mockMvc.perform(MockMvcRequestBuilders.post("/sub_category").content(objectMapper.writeValueAsString(created))
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("subCategory1"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(0))
               .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("description1"));
    }
}
