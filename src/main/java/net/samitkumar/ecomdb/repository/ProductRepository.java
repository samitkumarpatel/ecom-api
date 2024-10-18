package net.samitkumar.ecomdb.repository;

import net.samitkumar.ecomdb.entity.Product;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

import java.util.List;

public interface ProductRepository extends ListPagingAndSortingRepository<Product, Long>, ListCrudRepository<Product, Long> {
    List<Product> findByCategory(Long categoryId);
    List<Product> findByName(String name);
    List<Product> findByNameContaining(String name);
    List<Product> findByNameContainingIgnoreCase(String name);

}
