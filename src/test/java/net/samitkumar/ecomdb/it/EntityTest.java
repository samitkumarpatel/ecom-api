package net.samitkumar.ecomdb.it;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import net.samitkumar.ecomdb.TestcontainersConfiguration;
import net.samitkumar.ecomdb.entity.Cart;
import net.samitkumar.ecomdb.entity.Category;
import net.samitkumar.ecomdb.entity.Product;
import net.samitkumar.ecomdb.repository.CartRepository;
import net.samitkumar.ecomdb.repository.CategoryRepository;
import net.samitkumar.ecomdb.repository.ProductRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertAll;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
public class EntityTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("Entity Test")
    void entityTest() throws JsonProcessingException {
        //CATEGORY & PRODUCTS
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
                () -> categoryRepository.findAll().forEach(System.out::println),
                () -> categoryRepository.findById(1L).ifPresent(System.out::println),
                () -> productRepository.findAll().forEach(System.out::println),
                () -> productRepository.findById(1L).ifPresent(System.out::println)
        );

        // CARTS
        Map<String, Object> item1 = new HashMap<>();
        item1.put("productId", 1);
        item1.put("name", "Milk");
        item1.put("quantity", 1);
        item1.put("price", 10.5);

        Map<String, Object> item2 = new HashMap<>();
        item2.put("productId", 6);
        item2.put("name", "Chocolate");
        item2.put("quantity", 3);
        item2.put("price", 6.5);

        // Add items to the payload list
        List<Map<String, Object>> payload = List.of(item1, item2);
        var json = objectMapper.writeValueAsString(payload);
        System.out.println(json);
        cartRepository.save(new Cart(null, 1L, Map.of("items", json)));

        assertAll(
                () -> cartRepository.findAll().forEach(System.out::println),
                () -> cartRepository.findById(1L).ifPresent(System.out::println)
        );
    }

}
