package com.github.mdeluise.everymoney.movements.outcome;

import com.github.mdeluise.everymoney.movements.MovementDTO;

import java.util.Optional;

public class OutcomeDTO extends MovementDTO {
    private long categoryId;
    private Optional<Long> subCategoryId = Optional.empty();


    public OutcomeDTO() {
    }


    public long getCategoryId() {
        return categoryId;
    }


    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }


    public Optional<Long> getSubCategoryId() {
        return subCategoryId;
    }


    public void setSubCategoryId(Optional<Long> subCategoryId) {
        this.subCategoryId = subCategoryId;
    }
}
