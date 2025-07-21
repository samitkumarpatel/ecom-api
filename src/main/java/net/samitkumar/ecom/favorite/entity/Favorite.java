package net.samitkumar.ecom.favorite.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

@Table("favorites")
@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class Favorite {
    @Id
    private Long id;
    private Long userId;
    @MappedCollection(idColumn = "favorite_id")
    private Set<FavoriteProductRef> products;
}
