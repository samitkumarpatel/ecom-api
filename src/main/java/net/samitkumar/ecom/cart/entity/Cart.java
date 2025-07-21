package net.samitkumar.ecom.cart.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;
//User entity can be read like this - @ReadOnlyProperty @MappedCollection(idColumn = "id") User userId
@Table("carts")

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class Cart {
    @Id
    private Long id;
    private Long userId;
    @MappedCollection(idColumn = "cart_id", keyColumn = "product_id")
    private Set<CartProductRef> cartItems;
}
