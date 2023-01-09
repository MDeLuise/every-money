package com.github.mdeluise.everymoney.category;

import com.github.mdeluise.everymoney.category.subcategory.SubCategoryDTO;
import com.github.mdeluise.everymoney.movements.outcome.OutcomeDTO;

import java.util.HashSet;
import java.util.Set;

public class CategoryDTO {
    private Long id;
    private String name;
    private String description;
    private Set<OutcomeDTO> outcomes = new HashSet<>();
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
