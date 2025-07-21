package net.samitkumar.ecom.catelog.repository;

import net.samitkumar.ecom.catelog.entity.Category;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

//@RepositoryRestResource(collectionResourceRel = "category", path = "catalog")
public interface CategoryRepository extends ListPagingAndSortingRepository<Category, Long>, ListCrudRepository<Category, Long> {
}
