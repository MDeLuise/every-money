package com.github.mdeluise.everymoney.processor;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Represents a statistic regarding categories.")
public record CategoryStatistic(
    @Schema(description = "ID of the category.") Long id,
    @Schema(description = "Name of the category.", example = "Grocery") String name,
    @Schema(description = "Value of the category's statistic.", example = "42.1") Number value) {
}
