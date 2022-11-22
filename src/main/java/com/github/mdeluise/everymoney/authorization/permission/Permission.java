package com.github.mdeluise.everymoney.authorization.permission;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "permissions")
public class Permission {
    @Id
    @GeneratedValue
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private PType type;

    private String resourceClassName;

    private String resourceId;


    public Permission() {
    }


    public Permission(PType type, String resourceClassName, String resourceId) {
        this.type = type;
        this.resourceClassName = resourceClassName;
        this.resourceId = resourceId;
    }


    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }


    public PType getType() {
        return type;
    }


    public void setType(PType type) {
        this.type = type;
    }


    public String getResourceClass() {
        return resourceClassName;
    }


    public void setResourceClass(String resourceClassName) {
        this.resourceClassName = resourceClassName;
    }


    public String getResourceId() {
        return resourceId;
    }


    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }


    @Override
    public String toString() {
        return String.format(
            "%s:%s:%s", type.toString().toLowerCase(), resourceClassName, resourceId);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Permission p) {
            return this.resourceId.equals(p.resourceId) &&
                       this.resourceClassName.equals(p.resourceClassName) &&
                       this.type.equals(p.type);
        } else if (o instanceof String s) {
            return toString().equals(s);
        }
        return false;
    }


    @Override
    public int hashCode() {
        return Objects.hash(type, resourceClassName, resourceId);
    }
}
