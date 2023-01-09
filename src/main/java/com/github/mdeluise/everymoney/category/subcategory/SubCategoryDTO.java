package com.github.mdeluise.everymoney.category.subcategory;

import com.github.mdeluise.everymoney.movements.outcome.OutcomeDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Collection;
import java.util.HashSet;

@Schema(name = "SubCategory", description = "Represents a sub category of a Category.")
public class SubCategoryDTO {
    @Schema(description = "ID of the sub category.")
    private Long id;
    @Schema(description = "Name of the sub category.", example = "Food")
    private String name;
    @Schema(description = "Description of the sub category.", example = "Use this for grocery food", nullable = true)
    private String description;
    @Schema(description = "Parent category ID.")
    private long categoryId;
    @Schema(description = "Outcomes belonging to this sub category.", nullable = true)
    private Collection<OutcomeDTO> outcomes = new HashSet<>();


    public SubCategoryDTO() {
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public Long getCategoryId() {
        return categoryId;
    }


    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }


    public Collection<OutcomeDTO> getOutcomes() {
        return outcomes;
    }


    public void setOutcomes(Collection<OutcomeDTO> outcomes) {
        this.outcomes = outcomes;
    }
}
