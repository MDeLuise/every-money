package com.github.mdeluise.everymoney.movements;

import com.github.mdeluise.everymoney.wallet.Wallet;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractMovement {
    @Id
    @GeneratedValue
    private Long id;
    private double amount;
    private String description;
    @NotNull
    private Instant date = Instant.now();
    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "counterpart_id")
    private AbstractMovement counterpart;


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public double getAmount() {
        return amount;
    }


    public void setAmount(double amount) {
        this.amount = amount;
    }


    public Instant getDate() {
        return date;
    }


    public void setDate(Instant date) {
        this.date = date;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public Wallet getWallet() {
        return wallet;
    }


    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }


    public AbstractMovement getCounterpart() {
        return counterpart;
    }


    public void setCounterpart(AbstractMovement counterpart) {
        this.counterpart = counterpart;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AbstractMovement that = (AbstractMovement) o;
        return Double.compare(that.amount, amount) == 0 && Objects.equals(description, that.description) &&
                   wallet.equals(that.wallet);
    }


    @Override
    public int hashCode() {
        return Objects.hash(amount, description, wallet);
    }
}
