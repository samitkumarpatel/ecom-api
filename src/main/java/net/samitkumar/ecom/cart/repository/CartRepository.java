package net.samitkumar.ecom.cart.repository;

import net.samitkumar.ecom.cart.entity.Cart;
import org.springframework.data.repository.ListCrudRepository;

public interface CartRepository extends ListCrudRepository<Cart, Long> {
}
