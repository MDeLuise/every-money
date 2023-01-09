package com.github.mdeluise.everymoney.wallet;

import com.github.mdeluise.everymoney.movements.MovementDTO;

import java.util.HashSet;
import java.util.Set;

public class WalletDTO {
    private Long id;
    private String name;
    private String description;
    private double startingAmount;
    private Set<MovementDTO> movements = new HashSet<>();


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


    public Set<MovementDTO> getMovements() {
        return movements;
    }


    public void setMovements(Set<MovementDTO> movements) {
        this.movements = movements;
    }
}
