package net.samitkumar.ecomdb.repository;

import net.samitkumar.ecomdb.entity.Cart;
import org.springframework.data.repository.ListCrudRepository;

public interface CartRepository extends ListCrudRepository<Cart, Long> { }
