package com.github.mdeluise.everymoney.category.subcategory;

import com.github.mdeluise.everymoney.category.Category;
import com.github.mdeluise.everymoney.movements.outcome.Outcome;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "sub_categories")
public class SubCategory {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    @NotBlank
    private String name;
    private String description;
    @ManyToOne(optional = false)
    private Category category;
    @OneToMany(mappedBy = "id")
    private Set<Outcome> outcomes = new HashSet<>();


    public SubCategory(String name, String description, Category category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }


    public SubCategory() {
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


    public Category getCategory() {
        return category;
    }


    public void setCategory(Category category) {
        this.category = category;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SubCategory that = (SubCategory) o;
        return Objects.equals(name, that.name) && Objects.equals(description, that.description) &&
                   category.equals(that.category) && outcomes.equals(that.outcomes);
    }


    @Override
    public int hashCode() {
        return Objects.hash(name, description, category);
    }
}
