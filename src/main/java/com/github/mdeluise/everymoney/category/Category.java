package com.github.mdeluise.everymoney.category;

import com.github.mdeluise.everymoney.category.subcategory.SubCategory;
import com.github.mdeluise.everymoney.movements.outcome.Outcome;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    @NotBlank
    private String name;
    private String description;
    @OneToMany(mappedBy = "category")
    private Set<Outcome> outcomes = new HashSet<>();
    @OneToMany(mappedBy = "category")
    private Set<SubCategory> subCategories = new HashSet<>();


    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }


    public Category() {
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


    public Set<Outcome> getOutcomes() {
        return outcomes;
    }


    public void setOutcomes(Set<Outcome> outcomes) {
        this.outcomes = outcomes;
    }


    public Set<SubCategory> getSubCategories() {
        return subCategories;
    }


    public void setSubCategories(Set<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Category category = (Category) o;
        return name.equals(category.name) && Objects.equals(description, category.description) &&
                   outcomes.equals(category.outcomes);
    }


    @Override
    public int hashCode() {
        return Objects.hash(name, description, outcomes);
    }
}
