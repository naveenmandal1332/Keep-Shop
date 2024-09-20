package com.naveen.ecommerce.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProducRepository extends JpaRepository<Product, Integer> {

  List<Product> findAllByIdInOrderById(List<Integer> productIds);
}
