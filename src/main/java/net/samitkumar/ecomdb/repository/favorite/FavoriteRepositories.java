package net.samitkumar.ecomdb.repository.favorite;

import net.samitkumar.ecomdb.entity.favorite.Favorite;
import org.springframework.data.repository.ListCrudRepository;

public interface FavoriteRepositories extends ListCrudRepository<Favorite, Long> {
}
