package com.github.mdeluise.everymoney.movements.income;

import com.github.mdeluise.everymoney.movements.MovementDTO;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Income", description = "Represents a income.")
public class IncomeDTO extends MovementDTO {

    public IncomeDTO() {
    }
}


