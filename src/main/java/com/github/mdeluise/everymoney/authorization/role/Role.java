package com.github.mdeluise.everymoney.authorization.role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;


    public Role() {
    }


    public Role(ERole name) {
        this.name = name;
    }


    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }


    public ERole getName() {
        return name;
    }


    public void setName(ERole name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return name.toString();
    }
}
