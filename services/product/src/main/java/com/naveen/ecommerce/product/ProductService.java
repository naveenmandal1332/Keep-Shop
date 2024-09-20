package com.naveen.ecommerce.product;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

import org.springframework.stereotype.Service;
import com.naveen.ecommerce.exception.ProductPurchaseException;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProducRepository repository;
  private final ProductMapper mapper;

  public Integer createProduct(ProductRequest request) {
    var product = mapper.toProduct(request);
    return repository.save(product).getId();
  }

  public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> request) {

    // First Get All the Product Request Id:
    var productIds = request.stream()
        .map(ProductPurchaseRequest::productId)
        .toList();

    // Check All the product in db:
    var storedProducts = repository.findAllByIdInOrderById(productIds);
    if (productIds.size() != storedProducts.size())
      throw new ProductPurchaseException("One or more product does not exist!");

    // Sort The Product by Id:
    var storedRequest = request.stream()
        .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
        .toList();

    // Check Exist quantity and request quantity:
    var purchaseProducts = new ArrayList<ProductPurchaseResponse>();
    for (int i = 0; i < storedProducts.size(); i++) {
      var product = storedProducts.get(i);
      var requestProduct = storedRequest.get(i);

      if (product.getAvailableQuantity() < requestProduct.quantity())
        throw new ProductPurchaseException("Insufficient stock!");

      // Update The Quantity:
      var newAvailableQuantity = product.getAvailableQuantity() - requestProduct.quantity();
      product.setAvailableQuantity(newAvailableQuantity);
      repository.save(product);

      purchaseProducts.add(mapper.toProductPurchaseResponse(product, requestProduct.quantity()));
    }

    return purchaseProducts;
  }

  public ProductResponse findById(Integer productId) {
    return repository.findById(productId)
        .map(mapper::toProductResponse)
        .orElseThrow(() -> new EntityNotFoundException("Product Not Found with the ID:: " + productId));
  }

  public List<ProductResponse> findAll() {
    return repository.findAll().stream()
        .map(mapper::toProductResponse)
        .collect(Collectors.toList());
  }
}
