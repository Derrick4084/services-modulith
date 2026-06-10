package com.derocode.EcommApp.product.repositories;

import com.derocode.EcommApp.product.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CategoryRepository extends JpaRepository<Category,Long> {
    Optional<Category> findByName(String name);
}
