package net.samitkumar.ecomdb.entity.cart;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;
//User entity can be read like this - @ReadOnlyProperty @MappedCollection(idColumn = "id") User userId
@Table("carts")
public record Cart(@Id Long id, Long userId , @MappedCollection(idColumn = "cart_id", keyColumn = "product_id") Set<CartProductRef> cartItems){
}
