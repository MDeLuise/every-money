package com.github.mdeluise.everymoney.wallet;

import com.github.mdeluise.everymoney.movements.MovementDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.HashSet;
import java.util.Set;

@Schema(name = "Wallet", description = "Represents a wallet.")
public class WalletDTO {
    @Schema(description = "ID of the wallet.")
    private Long id;
    @Schema(description = "Name of the wallet.", example = "Bank 1")
    private String name;
    @Schema(description = "Description of the wallet.", example = "Unicredit bank", nullable = true)
    private String description;
    @Schema(description = "Starting amount of the wallet", defaultValue = "0.0")
    private double startingAmount;
    @Schema(description = "Movements belonging to this wallet.")
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
