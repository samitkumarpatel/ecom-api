package net.samitkumar.ecom.favorite.repository;

import net.samitkumar.ecom.favorite.entity.Favorite;
import org.springframework.data.repository.ListCrudRepository;

public interface FavoriteRepository extends ListCrudRepository<Favorite, Long> {
}
