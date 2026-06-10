package com.derocode.EcommApp.product.repositories;

import com.derocode.EcommApp.product.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {

    Optional<Product> getProductById(Long productId);

    Optional<Product> getProductByName(String name);
}
