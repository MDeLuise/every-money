package com.github.mdeluise.everymoney.movements.outcome;

import java.time.Instant;
import java.util.Optional;

public class OutcomeDTO {
    private long id;
    private double amount;
    private String description;
    private long categoryId;
    private Optional<Long> subCategoryId;
    private Instant date;
    private long walletId;
    private Long transferCounterpartId;


    public OutcomeDTO() {
    }


    public OutcomeDTO(Outcome income) {
        this.id = income.getId();
        this.amount = income.getAmount();
        this.description = income.getDescription();
        this.date = income.getDate();
        this.walletId = income.getWallet().getId();
        if (income.getCounterpart() != null) {
            this.transferCounterpartId = income.getCounterpart().getId();
        }
    }


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


    public long getCategoryId() {
        return categoryId;
    }


    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }


    public Optional<Long> getSubCategoryId() {
        return subCategoryId;
    }


    public void setSubCategoryId(Optional<Long> subCategoryId) {
        this.subCategoryId = subCategoryId;
    }


    public Long getTransferCounterpartId() {
        return transferCounterpartId;
    }


    public void setTransferCounterpartId(Long transferCounterpartId) {
        this.transferCounterpartId = transferCounterpartId;
    }
}
