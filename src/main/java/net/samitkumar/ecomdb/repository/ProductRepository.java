package net.samitkumar.ecomdb.repository;

import net.samitkumar.ecomdb.entity.Product;
import org.springframework.data.repository.ListCrudRepository;

public interface ProductRepository extends ListCrudRepository<Product, Long> {
}
