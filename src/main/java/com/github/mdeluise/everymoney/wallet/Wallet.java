package com.github.mdeluise.everymoney.wallet;

import com.github.mdeluise.everymoney.movements.AbstractMovement;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "wallets")
public class Wallet {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    @NotBlank
    private String name;
    private String description;
    private double startingAmount;
    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<AbstractMovement> movements = new HashSet<>();


    public Wallet() {
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


    public double getStartingAmount() {
        return startingAmount;
    }


    public void setStartingAmount(double startingAmount) {
        this.startingAmount = startingAmount;
    }


    public Set<AbstractMovement> getMovements() {
        return movements;
    }


    public void setMovements(Set<AbstractMovement> movements) {
        this.movements = movements;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Wallet wallet = (Wallet) o;
        return name.equals(wallet.name) && Objects.equals(description, wallet.description) &&
                   movements.equals(wallet.movements);
    }


    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }
}
