package net.samitkumar.ecomdb.repository;

import net.samitkumar.ecomdb.entity.Category;
import net.samitkumar.ecomdb.entity.Product;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

//@RepositoryRestResource(collectionResourceRel = "category", path = "catalog")
public interface CategoryRepository extends ListPagingAndSortingRepository<Category, Long>, ListCrudRepository<Category, Long> {
    @RestResource
    List<Category> findByName(String name);
    @RestResource
    List<Category> findByNameContaining(String name);
}
