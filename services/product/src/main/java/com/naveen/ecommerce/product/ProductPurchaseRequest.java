package com.naveen.ecommerce.product;

import jakarta.validation.constraints.NotNull;

public record ProductPurchaseRequest(
    @NotNull(message = "Product Id cannot be Null!") Integer productId,

    @NotNull(message = "Product Quantity cannot be Null!") double quantity) {

}
