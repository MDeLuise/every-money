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
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractMovement {
    @Id
    @GeneratedValue
    private Long id;
    private double amount;
    @Min(1)
    private int quantity = 1;
    private String description;
    private String note;
    @NotNull
    private Instant date = Instant.now();
    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;
    @OneToOne(cascade = CascadeType.ALL, optional = true)
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


    public double getTotalAmount() {
        return amount * quantity;
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


    public Optional<AbstractMovement> getCounterpart() {
        return counterpart != null ? Optional.of(counterpart) : Optional.empty();
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
        return Double.compare(that.amount, amount) == 0 && quantity == that.quantity &&
                   Objects.equals(description, that.description) && Objects.equals(note, that.note) &&
                   Objects.equals(date, that.date) && Objects.equals(wallet, that.wallet);
    }


    @Override
    public int hashCode() {
        return Objects.hash(amount, quantity, description, note, date, wallet);
    }
}
