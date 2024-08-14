package net.samitkumar.ecomdb.entity.favorite;

import org.springframework.data.relational.core.mapping.Table;

@Table("favorite_product_refs")
public record FavoriteProductRef(Long favoriteId, Long productId) {
}
