package net.samitkumar.ecomdb.repository.cart;

import net.samitkumar.ecomdb.entity.cart.Cart;
import org.springframework.data.repository.ListCrudRepository;

public interface CartRepositories extends ListCrudRepository<Cart, Long> {
}
