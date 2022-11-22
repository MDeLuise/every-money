package com.github.mdeluise.everymoney.movements.income;

import com.github.mdeluise.everymoney.movements.AbstractMovement;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "incomes")
public class Income extends AbstractMovement {
}
