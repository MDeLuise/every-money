package com.github.mdeluise.everymoney.movements;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.Optional;

@Schema(name = "Movement", description = "Represents a movement, can be an Income or an Outcome.")
public class MovementDTO {
    public enum MovementDTOType {
        INCOME, OUTCOME
    }

    @Schema(description = "ID of the movement.")
    private long id;
    @Schema(description = "Amount of the movement.", defaultValue = "0")
    private double amount;
    @Schema(description = "How many movements.", defaultValue = "1")
    private int quantity = 1;
    @Schema(description = "Description of the movement.", example = "Bread", nullable = true)
    private String description;
    @Schema(description = "Note of the movement.", example = "null", nullable = true)
    private String note;
    @Schema(description = "Time of the movement.")
    private Instant date = Instant.now();
    @Schema(description = "ID of the wallet belonging to.")
    private long walletId;
    @Schema(description = "ID of the counterpart movement (if this is a transfer between wallets).", example = "null",
        nullable = true)
    private Optional<Long> transferCounterpartId = Optional.empty();
    @Schema(description = "Type of the movement.", example = "OUTCOME")
    private MovementDTOType type;


    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }


    public double getAmount() {
        return amount;
    }


    public void setAmount(double amount) {
        this.amount = amount;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public Instant getDate() {
        return date;
    }


    public void setDate(Instant date) {
        this.date = date;
    }


    public long getWalletId() {
        return walletId;
    }


    public void setWalletId(long walletId) {
        this.walletId = walletId;
    }


    public Optional<Long> getTransferCounterpartId() {
        return transferCounterpartId;
    }


    public void setTransferCounterpartId(Optional<Long> transferCounterpartId) {
        this.transferCounterpartId = transferCounterpartId;
    }


    public MovementDTOType getType() {
        return type;
    }


    public void setType(MovementDTOType type) {
        this.type = type;
    }


    public int getQuantity() {
        return quantity;
    }


    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public String getNote() {
        return note;
    }


    public void setNote(String note) {
        this.note = note;
    }
}
