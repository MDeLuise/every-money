package com.github.mdeluise.everymoney.movements.income;

import java.time.Instant;

public class IncomeDTO {
    private long id;
    private double amount;
    private String description;
    private Instant date;
    private long walletId;
    private Long transferCounterpartId;


    public IncomeDTO() {
    }


    public IncomeDTO(Income income) {
        this.id = income.getId();
        this.amount = income.getAmount();
        this.description = income.getDescription();
        this.date = income.getDate();
        if (income.getCounterpart() != null) {
            this.walletId = income.getWallet().getId();
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


    public Long getTransferCounterpartId() {
        return transferCounterpartId;
    }


    public void setTransferCounterpartId(Long transferCounterpartId) {
        this.transferCounterpartId = transferCounterpartId;
    }
}


