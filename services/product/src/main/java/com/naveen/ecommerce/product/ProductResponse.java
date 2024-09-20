package com.naveen.ecommerce.product;

import java.math.BigDecimal;

public record ProductResponse(
    Integer id,
    String name,
    String descriptio,
    double availableQuantity,
    BigDecimal price,
    Integer categoryId,
    String categoryName,
    String categoryDescription) {

}
