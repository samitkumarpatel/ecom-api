package net.samitkumar.ecomdb.it;

import net.samitkumar.ecomdb.TestcontainersConfiguration;
import net.samitkumar.ecomdb.entity.Category;
import net.samitkumar.ecomdb.entity.Product;
import net.samitkumar.ecomdb.repository.CategoryRepository;
import net.samitkumar.ecomdb.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertAll;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
public class EntityTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("Entity Test")
    void entityTest() {
        var categories = categoryRepository
                .saveAll(
                    List.of(
                        new Category(null, "Electronics", "Electronics Items", Set.of()),
                        new Category(null, "Clothing", "Clothing Items", null),
                        new Category(null, "Books", "Books Items", Set.of(
                                new Product(null, "Book 1", "Book 1 Description", 100.0, 5L, null)
                        ))
                    )
                );

        assertAll(
                () -> categories.forEach(System.out::println),
                () -> productRepository.findAll().forEach(System.out::println)
        );
    }

}
