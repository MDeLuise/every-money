package com.github.mdeluise.everymoney.movements;

import java.time.Instant;
import java.util.Optional;

public class MovementDTO {
    public enum MovementDTOType {
        INCOME, OUTCOME
    }

    private long id;
    private double amount;
    private int quantity = 1;
    private String description;
    private String note;
    private Instant date = Instant.now();
    private long walletId;
    private Optional<Long> transferCounterpartId = Optional.empty();
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
