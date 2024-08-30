package com.naveen.ecommerce.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CustomerRequest(
    String id,

    @NotNull(message = "Customer First Name is required!") String firstname,

    @NotNull(message = "Customer Last Name is required!") String lastname,

    @NotNull(message = "Customer email is required!") @Email(message = "Customer email is invalid!") String email,
    Address address) {

}
