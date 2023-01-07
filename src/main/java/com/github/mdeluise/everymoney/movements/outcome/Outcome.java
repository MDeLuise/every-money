package com.github.mdeluise.everymoney.movements.outcome;

import com.github.mdeluise.everymoney.category.Category;
import com.github.mdeluise.everymoney.category.subcategory.SubCategory;
import com.github.mdeluise.everymoney.movements.AbstractMovement;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

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


    public Category getCategory() {
        return category;
    }


    public void setCategory(Category category) {
        this.category = category;
    }


    public SubCategory getSubCategory() {
        return subCategory;
    }


    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }
}
