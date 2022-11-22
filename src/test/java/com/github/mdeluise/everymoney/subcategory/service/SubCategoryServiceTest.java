package com.github.mdeluise.everymoney.subcategory.service;

import com.github.mdeluise.everymoney.category.subcategory.SubCategory;
import com.github.mdeluise.everymoney.category.subcategory.SubCategoryRepository;
import com.github.mdeluise.everymoney.category.subcategory.SubCategoryService;
import com.github.mdeluise.everymoney.exception.EntityNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.Set;

@ExtendWith(SpringExtension.class)
@WithMockUser(username = "admin", roles = "ADMIN")
public class SubCategoryServiceTest {
    @Mock
    SubCategoryRepository subCategoryRepository;
    @InjectMocks
    SubCategoryService subCategoryService;


    @Test
    void whenSaveSubCategory_thenReturnSubCategory() {
        SubCategory toSave = new SubCategory();
        toSave.setId(0L);
        toSave.setName("subCategory0");
        toSave.setDescription("description0");
        Mockito.when(subCategoryRepository.save(toSave)).thenReturn(toSave);

        Assertions.assertThat(subCategoryService.save(toSave)).isSameAs(toSave);
    }


    @Test
    void whenGetSubCategory_thenReturnSubCategory() {
        SubCategory toGet = new SubCategory();
        toGet.setId(0L);
        toGet.setName("subCategory0");
        toGet.setDescription("description0");
        Mockito.when(subCategoryRepository.findById(0L)).thenReturn(Optional.of(toGet));

        Assertions.assertThat(subCategoryService.get(0L)).isSameAs(toGet);
    }


    @Test
    void whenGetNonExistingSubCategory_thenError() {
        Mockito.when(subCategoryRepository.findById(0L)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> subCategoryService.get(0L)).isInstanceOf(EntityNotFoundException.class);
    }


    @Test
    void whenGetAllCategories_thenReturnAllCategories() {
        SubCategory toGet1 = new SubCategory();
        toGet1.setId(0L);
        toGet1.setName("subCategory0");
        toGet1.setDescription("description0");
        SubCategory toGet2 = new SubCategory();
        toGet2.setId(1L);
        toGet2.setName("subCategory1");
        toGet2.setDescription("description1");

        Set<SubCategory> allCategories = Set.of(toGet1, toGet2);
        Mockito.when(subCategoryRepository.findAll()).thenReturn(allCategories);

        Assertions.assertThat(subCategoryService.getAll()).isSameAs(allCategories);
    }


    @Test
    void givenSubCategory_whenDeleteSubCategory_thenDeleteSubCategory() {
        Mockito.when(subCategoryRepository.existsById(0L)).thenReturn(true);
        subCategoryService.remove(0L);
        Mockito.verify(subCategoryRepository, Mockito.times(1)).deleteById(0L);
    }


    @Test
    void whenDeleteNonExistingSubCategory_thenError() {
        Mockito.when(subCategoryRepository.existsById(0L)).thenReturn(false);

        Assertions.assertThatThrownBy(() -> subCategoryService.remove(0L)).isInstanceOf(EntityNotFoundException.class);
    }


    @Test
    void givenSubCategory_whenUpdateSubCategory_thenUpdateSubCategory() {
        SubCategory updated = new SubCategory();
        updated.setName("updated");
        Mockito.when(subCategoryRepository.existsById(0L)).thenReturn(true);
        Mockito.when(subCategoryRepository.findById(0L)).thenReturn(Optional.of(new SubCategory()));
        Mockito.when(subCategoryRepository.save(Mockito.any())).thenReturn(updated);

        Assertions.assertThat(subCategoryService.update(0L, updated)).isSameAs(updated);
    }


    @Test
    void whenUpdateNonExistingSubCategory_thenError() {
        Mockito.when(subCategoryRepository.existsById(0L)).thenReturn(false);
        SubCategory updated = new SubCategory();
        updated.setName("updated");

        Assertions.assertThatThrownBy(() -> subCategoryService.update(0L, updated))
                  .isInstanceOf(EntityNotFoundException.class);
    }
}
