package com.github.mdeluise.everymoney.category.subcategory;

import com.github.mdeluise.everymoney.movements.outcome.OutcomeDTO;

import java.util.Collection;
import java.util.HashSet;

public class SubCategoryDTO {
    private Long id;
    private String name;
    private String description;
    private long categoryId;
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
