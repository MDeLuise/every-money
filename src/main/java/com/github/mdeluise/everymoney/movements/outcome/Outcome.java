package com.github.mdeluise.everymoney.movements.outcome;

import com.github.mdeluise.everymoney.category.Category;
import com.github.mdeluise.everymoney.category.subcategory.SubCategory;
import com.github.mdeluise.everymoney.movements.AbstractMovement;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

@Entity
@Table(name = "outcomes")
public class Outcome extends AbstractMovement {
    @NotNull
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    @ManyToOne
    @JoinColumn(name = "sub_category_id", nullable = true)
    private SubCategory subCategory;


    public Outcome() {
    }

    public Category getCategory() {
        return category;
    }


    public void setCategory(Category category) {
        this.category = category;
    }


    public Optional<SubCategory> getSubCategory() {
        return subCategory != null ? Optional.of(subCategory) : Optional.empty();
    }


    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }
}
