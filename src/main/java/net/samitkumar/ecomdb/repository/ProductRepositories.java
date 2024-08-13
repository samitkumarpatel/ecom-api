package net.samitkumar.ecomdb.repository;

import net.samitkumar.ecomdb.entity.Category;
import net.samitkumar.ecomdb.entity.Product;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface ProductRepositories extends ListCrudRepository<Product, Long> {
    List<Product> findByCategory(Long categoryId);
}
