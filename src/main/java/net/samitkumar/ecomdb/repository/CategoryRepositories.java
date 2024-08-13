package net.samitkumar.ecomdb.repository;

import net.samitkumar.ecomdb.entity.Category;
import org.springframework.data.repository.ListCrudRepository;

public interface CategoryRepositories extends ListCrudRepository<Category, Long> {
}
