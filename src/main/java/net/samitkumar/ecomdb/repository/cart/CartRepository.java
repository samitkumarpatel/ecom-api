package net.samitkumar.ecomdb.repository.cart;

import net.samitkumar.ecomdb.entity.cart.Cart;
import org.springframework.data.repository.ListCrudRepository;

public interface CartRepository extends ListCrudRepository<Cart, Long> {
}
