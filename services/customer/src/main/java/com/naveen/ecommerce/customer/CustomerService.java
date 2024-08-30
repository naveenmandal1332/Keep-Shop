package com.naveen.ecommerce.customer;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import com.naveen.ecommerce.exception.CustomerNotFoundException;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

  private final CustomerRepository repository;
  private final CustomerMapper mapper;

  public String createCustomer(CustomerRequest request) {
    var customer = repository.save(mapper.toCustomer(request));
    return customer.getId();
  }

  public void updateCustomer(CustomerRequest request) {
    var customer = repository.findById(request.id()).orElseThrow(() -> new CustomerNotFoundException(
        String.format("Cannot update customer:: No customer found with the proviced id:: %S", request.id())));

    mergeCustomer(customer, request);

    repository.save(customer);
  }

  private void mergeCustomer(Customer customer, CustomerRequest request) {

    if (StringUtils.isNotBlank(request.firstname())) {
      customer.setFirstname(request.firstname());
    }

    if (StringUtils.isNotBlank(request.lastname())) {
      customer.setLastname(request.lastname());
    }

    if (StringUtils.isNotBlank(request.email())) {
      customer.setEmail(request.email());
    }

    if (request.address() != null) {
      customer.setAddress(request.address());
    }

  }

  public List<CustomerResponse> findAllCustomer() {
    return repository.findAll()
        .stream()
        .map(mapper::fromCustomer)
        .collect(Collectors.toList());
  }
}
