package net.samitkumar.ecomdb.entity.favorite;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

@Table("favorites")
public record Favorite(@Id Long id, Long userId, @MappedCollection(idColumn = "favorite_id") Set<FavoriteProductRef> products) {
}
