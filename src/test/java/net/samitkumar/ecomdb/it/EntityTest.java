package net.samitkumar.ecomdb.it;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.samitkumar.ecomdb.TestcontainersConfiguration;
import net.samitkumar.ecomdb.entity.Category;
import net.samitkumar.ecomdb.entity.Product;
import net.samitkumar.ecomdb.entity.cart.Cart;
import net.samitkumar.ecomdb.entity.cart.CartProductRef;
import net.samitkumar.ecomdb.entity.order.Order;
import net.samitkumar.ecomdb.entity.order.OrderProductRef;
import net.samitkumar.ecomdb.repository.CategoryRepositories;
import net.samitkumar.ecomdb.repository.ProductRepositories;
import net.samitkumar.ecomdb.repository.UserRepositories;
import net.samitkumar.ecomdb.repository.cart.CartRepositories;
import net.samitkumar.ecomdb.repository.order.OrderRepository;
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
    CategoryRepositories categoriesRepositories;

    @Autowired
    ProductRepositories productsRepositories;

    @Autowired
    UserRepositories usersRepositories;

    @Autowired
    CartRepositories cartRepositories;

    @Autowired
    OrderRepository orderRepositories;

    @Test
    @DisplayName("Entity Test")
    void entityTest() {

        //USERS
        assertAll(
                () -> usersRepositories.findAll().forEach(System.out::println)
                /*
                User[id=1, name=John Doe, email=johndoe@dev.net]
                User[id=2, name=Jane Doe, email=janedoe@dev.net]
                */
        );

        //CATEGORIES
        var categories = categoriesRepositories.saveAll(
                List.of(
                        new Category(null, "Electronics", "Electronics Items", Set.of()),
                        new Category(null, "Clothing", "Clothing Items", Set.of(
                                new Product(null, null, "Blazer", "Suits", 10.0, 100)
                        )),
                        new Category(null, "Books", "Books", Set.of())
                )
        );

        assertAll(
                () -> categories.forEach(System.out::println),
                /*
                    Category[id=1, name=Electronics, description=Electronics Items, products=[]]
                    Category[id=2, name=Clothing, description=Clothing Items, products=[Product[id=1, category=null, name=Blazer, description=Suits, price=10.0, quantity=100]]]
                    Category[id=3, name=Books, description=Books, products=[]]
                */
                () -> categoriesRepositories.findAll().forEach(System.out::println),
                /*
                    Category[id=1, name=Electronics, description=Electronics Items, products=[]]
                    Category[id=2, name=Clothing, description=Clothing Items, products=[Product[id=1, category=2, name=Blazer, description=Suits, price=10.0, quantity=100]]]
                    Category[id=3, name=Books, description=Books, products=[]]
                */
                () -> categoriesRepositories.findById(1L).ifPresent(System.out::println)
                /*
                    Category[id=1, name=Electronics, description=Electronics Items, products=[]]
                */
        );

        // PRODUCTS
        var products = productsRepositories.saveAll(
                List.of(
                        new Product(null, categories.get(0).id(), "Laptop", "Laptop", 1000.0, 10),
                        new Product(null, categories.get(1).id(), "T-Shirt", "T-Shirt", 10.0, 100),
                        new Product(null, categories.get(2).id(), "Java Book", "Java Book", 20.0, 50)
                )
        );
        assertAll(
                () -> products.forEach(System.out::println),
                /*
                    Product[id=2, category=1, name=Laptop, description=Laptop, price=1000.0, quantity=10]
                    Product[id=3, category=2, name=T-Shirt, description=T-Shirt, price=10.0, quantity=100]
                    Product[id=4, category=3, name=Java Book, description=Java Book, price=20.0, quantity=50]
                 */
                () -> productsRepositories.findAll().forEach(System.out::println),
                /*
                    Product[id=1, category=2, name=Blazer, description=Suits, price=10.0, quantity=100]
                    Product[id=2, category=1, name=Laptop, description=Laptop, price=1000.0, quantity=10]
                    Product[id=3, category=2, name=T-Shirt, description=T-Shirt, price=10.0, quantity=100]
                    Product[id=4, category=3, name=Java Book, description=Java Book, price=20.0, quantity=50]
                 */
                () -> productsRepositories.findById(1L).ifPresent(System.out::println),
                /*
                    Product[id=1, category=2, name=Blazer, description=Suits, price=10.0, quantity=100]
                 */
                () -> productsRepositories.findByCategory(2L).forEach(System.out::println)
                /*
                    Product[id=1, category=2, name=Blazer, description=Suits, price=10.0, quantity=100]
                    Product[id=3, category=2, name=T-Shirt, description=T-Shirt, price=10.0, quantity=100]
                */
        );

        //CART
        var carts = cartRepositories.saveAll(
                List.of(
                        new Cart(null, 1L, Set.of(
                                new CartProductRef(null, products.get(0).id(), 2),
                                new CartProductRef(null, products.get(1).id(), 3)
                        )),
                        new Cart(null, 2L, Set.of(
                                new CartProductRef(null, products.get(1).id(), 1),
                                new CartProductRef(null, products.get(2).id(), 2)
                        ))
                )
        );
        assertAll(
                () -> carts.forEach(System.out::println),
                /*
                    Cart[id=1, userId=1, cartItems=[CartProductRef[cartId=null, productId=2, quantity=2], CartProductRef[cartId=null, productId=3, quantity=3]]]
                    Cart[id=2, userId=2, cartItems=[CartProductRef[cartId=null, productId=3, quantity=1], CartProductRef[cartId=null, productId=4, quantity=2]]]
                */
                () -> cartRepositories.findAll().forEach(System.out::println)
                /*
                    Cart[id=1, userId=1, cartItems=[CartProductRef[cartId=1, productId=2, quantity=2], CartProductRef[cartId=1, productId=3, quantity=3]]]
                    Cart[id=2, userId=2, cartItems=[CartProductRef[cartId=2, productId=3, quantity=1], CartProductRef[cartId=2, productId=4, quantity=2]]]
                */
        );

        //ORDER
        var orders = orderRepositories.saveAll(
                List.of(
                        new Order(null, 1L, Order.OrderStatus.PENDING, Set.of(
                                new OrderProductRef(null, products.get(0).id(), 2)
                        )),
                        new Order(null, 1L, Order.OrderStatus.PENDING, Set.of(
                                new OrderProductRef(null, products.get(1).id(), 3),
                                new OrderProductRef(null, products.get(2).id(), 1)
                        )),
                        new Order(null, 2L, Order.OrderStatus.PENDING, Set.of(
                                new OrderProductRef(null, products.get(2).id(), 2)
                        ))
                )
        );
        assertAll(
                () -> orders.forEach(System.out::println),
                /*
                    Order[id=1, userId=1, status=PENDING, products=[OrderProductRef[orderId=null, productId=2, quantity=2]]]
                    Order[id=2, userId=1, status=PENDING, products=[OrderProductRef[orderId=null, productId=3, quantity=3], OrderProductRef[orderId=null, productId=4, quantity=1]]]
                    Order[id=3, userId=2, status=PENDING, products=[OrderProductRef[orderId=null, productId=4, quantity=2]]]
                */
                () -> orderRepositories.findAll().forEach(System.out::println),
                /*
                    Order[id=1, userId=1, status=PENDING, products=[OrderProductRef[orderId=1, productId=2, quantity=2]]]
                    Order[id=2, userId=1, status=PENDING, products=[OrderProductRef[orderId=2, productId=3, quantity=3], OrderProductRef[orderId=2, productId=4, quantity=1]]]
                    Order[id=3, userId=2, status=PENDING, products=[OrderProductRef[orderId=3, productId=4, quantity=2]]]
                */
                () -> orderRepositories.findById(1L).ifPresent(System.out::println),
                /*
                    Order[id=1, userId=1, status=PENDING, products=[OrderProductRef[orderId=1, productId=2, quantity=2]]]
                */
                () -> orderRepositories.findByUserId(1L).forEach(System.out::println)
                /*
                    Order[id=1, userId=1, status=PENDING, products=[OrderProductRef[orderId=1, productId=2, quantity=2]]]
                    Order[id=2, userId=1, status=PENDING, products=[OrderProductRef[orderId=2, productId=3, quantity=3], OrderProductRef[orderId=2, productId=4, quantity=1]]]
                */
        );

    }

}
