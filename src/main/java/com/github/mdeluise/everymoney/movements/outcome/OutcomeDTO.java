package com.github.mdeluise.everymoney.movements.outcome;

import com.github.mdeluise.everymoney.movements.MovementDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Optional;

@Schema(name = "Outcome", description = "Represents an outcome.")
public class OutcomeDTO extends MovementDTO {
    @Schema(description = "ID of the category belonging to.")
    private long categoryId;
    @Schema(description = "ID of the sub category belonging to.", nullable = true)
    private Optional<Long> subCategoryId = Optional.empty();
    @Schema(description = "Represent if the outcome was a discount or not.", defaultValue = "false")
    private boolean discount;


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


    public boolean isDiscount() {
        return discount;
    }


    public void setDiscount(boolean discount) {
        this.discount = discount;
    }
}
