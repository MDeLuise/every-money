package com.github.mdeluise.everymoney.movements.outcome;

import com.github.mdeluise.everymoney.category.Category;
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
    private boolean discount;


    public Category getCategory() {
        return category;
    }


    public void setCategory(Category category) {
        this.category = category;
    }


    public boolean isDiscount() {
        return discount;
    }


    public void setDiscount(boolean discount) {
        this.discount = discount;
    }
}
