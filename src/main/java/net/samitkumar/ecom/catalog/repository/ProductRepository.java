package net.samitkumar.ecom.catalog.repository;

import net.samitkumar.ecom.catalog.entity.Product;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends ListPagingAndSortingRepository<Product, Long>, ListCrudRepository<Product, Long> {
    List<Product> findByCategory(Long categoryId);
    Optional<Product> findProductByCategoryAndId(Long categoryId, Long productId);
}
