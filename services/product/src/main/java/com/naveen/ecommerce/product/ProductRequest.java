package com.naveen.ecommerce.product;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductRequest(
    Integer id,

    @NotNull(message = "Product name cannot be null!") String name,

    @NotNull(message = "Product description cannot be null!") String description,

    @Positive(message = "Available quantity should be positive!") double availableQuantity,

    @Positive(message = "Product price should be positive!") BigDecimal price,

    @NotNull(message = "Product categoryId is required!") Integer categoryId) {

}
