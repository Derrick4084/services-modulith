package com.derocode.EcommApp.cart.repository;


import com.derocode.EcommApp.cart.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {

    Optional<Cart> findByCustomerEmail(String customerEmail);

}
