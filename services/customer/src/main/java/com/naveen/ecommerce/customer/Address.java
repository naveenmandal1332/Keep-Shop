package com.naveen.ecommerce.customer;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.annotation.Validated;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
@Document
@Validated
public class Address {
  private String street;
  private String houseNumber;
  private String zipCode;

}
