package net.samitkumar.ecom.catalog.repository;

import net.samitkumar.ecom.catalog.entity.Category;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface CategoryRepository extends ListPagingAndSortingRepository<Category, Long>, ListCrudRepository<Category, Long> {
}
