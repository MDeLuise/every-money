package com.github.mdeluise.everymoney.category;

import com.github.mdeluise.everymoney.category.subcategory.SubCategoryDTO;
import com.github.mdeluise.everymoney.movements.outcome.OutcomeDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.HashSet;
import java.util.Set;

@Schema(name = "Category", description = "Represent a category of outcomes.")
public class CategoryDTO {
    @Schema(description = "ID of the category.")
    private Long id;
    @Schema(description = "Name of the category.", example = "Grocery")
    private String name;
    @Schema(description = "Description of the category.", example = "Use this for grocery items", nullable = true)
    private String description;
    @Schema(description = "Outcomes belonging to this category.", nullable = true)
    private Set<OutcomeDTO> outcomes = new HashSet<>();
    @Schema(description = "Sub categories belonging to this category.", nullable = true)
    private Set<SubCategoryDTO> subCategories = new HashSet<>();


    public CategoryDTO() {
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


    public Set<OutcomeDTO> getOutcomes() {
        return outcomes;
    }


    public void setOutcomes(Set<OutcomeDTO> outcomes) {
        this.outcomes = outcomes;
    }


    public Set<SubCategoryDTO> getSubCategories() {
        return subCategories;
    }


    public void setSubCategories(Set<SubCategoryDTO> subCategories) {
        this.subCategories = subCategories;
    }
}
