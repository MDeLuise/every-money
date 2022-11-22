package com.github.mdeluise.everymoney.category.service;

import com.github.mdeluise.everymoney.category.Category;
import com.github.mdeluise.everymoney.category.CategoryRepository;
import com.github.mdeluise.everymoney.category.CategoryService;
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
public class CategoryServiceTest {
    @Mock
    CategoryRepository categoryRepository;
    @InjectMocks
    CategoryService categoryService;


    @Test
    void whenSaveCategory_thenReturnCategory() {
        Category toSave = new Category();
        toSave.setId(0L);
        toSave.setName("category0");
        toSave.setDescription("description0");
        Mockito.when(categoryRepository.save(toSave)).thenReturn(toSave);

        Assertions.assertThat(categoryService.save(toSave)).isSameAs(toSave);
    }


    @Test
    void whenGetCategory_thenReturnCategory() {
        Category toGet = new Category();
        toGet.setId(0L);
        toGet.setName("category0");
        toGet.setDescription("description0");
        Mockito.when(categoryRepository.findById(0L)).thenReturn(Optional.of(toGet));

        Assertions.assertThat(categoryService.get(0L)).isSameAs(toGet);
    }


    @Test
    void whenGetNonExistingCategory_thenError() {
        Mockito.when(categoryRepository.findById(0L)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> categoryService.get(0L)).isInstanceOf(EntityNotFoundException.class);
    }


    @Test
    void whenGetAllCategories_thenReturnAllCategories() {
        Category toGet1 = new Category();
        toGet1.setId(0L);
        toGet1.setName("category0");
        toGet1.setDescription("description0");
        Category toGet2 = new Category();
        toGet2.setId(1L);
        toGet2.setName("category1");
        toGet2.setDescription("description1");

        Set<Category> allCategories = Set.of(toGet1, toGet2);
        Mockito.when(categoryRepository.findAll()).thenReturn(allCategories);

        Assertions.assertThat(categoryService.getAll()).isSameAs(allCategories);
    }


    @Test
    void givenCategory_whenDeleteCategory_thenDeleteCategory() {
        Mockito.when(categoryRepository.existsById(0L)).thenReturn(true);
        categoryService.remove(0L);
        Mockito.verify(categoryRepository, Mockito.times(1)).deleteById(0L);
    }


    @Test
    void whenDeleteNonExistingCategory_thenError() {
        Mockito.when(categoryRepository.existsById(0L)).thenReturn(false);

        Assertions.assertThatThrownBy(() -> categoryService.remove(0L)).isInstanceOf(EntityNotFoundException.class);
    }


    @Test
    void givenCategory_whenUpdateCategory_thenUpdateCategory() {
        Category updated = new Category();
        updated.setName("updated");
        Mockito.when(categoryRepository.existsById(0L)).thenReturn(true);
        Mockito.when(categoryRepository.findById(0L)).thenReturn(Optional.of(new Category()));
        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(updated);

        Assertions.assertThat(categoryService.update(0L, updated)).isSameAs(updated);
    }


    @Test
    void whenUpdateNonExistingCategory_thenError() {
        Mockito.when(categoryRepository.existsById(0L)).thenReturn(false);
        Category updated = new Category();
        updated.setName("updated");

        Assertions.assertThatThrownBy(() -> categoryService.update(0L, updated))
                  .isInstanceOf(EntityNotFoundException.class);
    }


}
