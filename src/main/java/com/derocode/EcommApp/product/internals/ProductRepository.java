package com.derocode.EcommApp.product.internals;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {

    Optional<Product> getProductById(Long productId);

    Optional<Product> getProductByName(String name);
}
