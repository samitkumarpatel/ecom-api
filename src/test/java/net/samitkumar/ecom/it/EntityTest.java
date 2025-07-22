package net.samitkumar.ecom.it;

import net.samitkumar.ecom.TestcontainersConfiguration;
import net.samitkumar.ecom.catalog.entity.Category;
import net.samitkumar.ecom.catalog.entity.Inventory;
import net.samitkumar.ecom.catalog.entity.Product;
import net.samitkumar.ecom.cart.entity.Cart;
import net.samitkumar.ecom.cart.entity.CartProductRef;
import net.samitkumar.ecom.favorite.entity.Favorite;
import net.samitkumar.ecom.favorite.entity.FavoriteProductRef;
import net.samitkumar.ecom.order.entity.Order;
import net.samitkumar.ecom.order.entity.OrderProductRef;
import net.samitkumar.ecom.catalog.repository.CategoryRepository;
import net.samitkumar.ecom.catalog.repository.ProductRepository;
import net.samitkumar.ecom.admin.repository.UserRepository;
import net.samitkumar.ecom.cart.repository.CartRepository;
import net.samitkumar.ecom.favorite.repository.FavoriteRepository;
import net.samitkumar.ecom.order.repository.OrderRepository;
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
    CategoryRepository categoriesRepository;

    @Autowired
    ProductRepository productsRepository;

    @Autowired
    UserRepository usersRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    FavoriteRepository favoriteRepository;

    @Test
    @DisplayName("Entity Test")
    void entityTest() {

        //USERS
        assertAll(
                () -> usersRepository.findAll().forEach(System.out::println)
                /*
                User[id=1, name=John Doe, email=johndoe@dev.net]
                User[id=2, name=Jane Doe, email=janedoe@dev.net]
                */
        );

        //CATEGORIES
        var categories = categoriesRepository.saveAll(
                List.of(
                        new Category(null, "Electronics", "Electronics Items", Set.of()),
                        new Category(null, "Clothing", "Clothing Items", Set.of(
                                new Product(null, null, "Blazer", "Suits", 10.0, new Inventory(null, null, 100))
                        )),
                        new Category(null, "Books", "Books", Set.of())
                )
        );

        assertAll(
                () -> categories.forEach(System.out::println),
                /*
                    Category[id=1, name=Electronics, description=Electronics Items, products=[]]
                    Category[id=2, name=Clothing, description=Clothing Items, products=[Product[id=1, category=null, name=Blazer, description=Suits, price=10.0, inventory=Inventory[id=1, productId=null, quantity=100]]]]
                    Category[id=3, name=Books, description=Books, products=[]]
                */
                () -> categoriesRepository.findAll().forEach(System.out::println),
                /*
                    Category[id=1, name=Electronics, description=Electronics Items, products=[]]
                    Category[id=2, name=Clothing, description=Clothing Items, products=[Product[id=1, category=2, name=Blazer, description=Suits, price=10.0, inventory=Inventory[id=1, productId=1, quantity=100]]]]
                    Category[id=3, name=Books, description=Books, products=[]]
                */
                () -> categoriesRepository.findById(1L).ifPresent(System.out::println)
                /*
                    Category[id=1, name=Electronics, description=Electronics Items, products=[]]
                */
        );

        var changecategory = categoriesRepository.save(new Category(1L, "Electronics", "Electronics Goods", Set.of()));
        assertAll(
                () -> System.out.println(changecategory),
                () -> categoriesRepository.findById(1L).ifPresent(System.out::println)
                /*
                    Category[id=1, name=Electronics, description=Electronics Goods, products=[]]
                */
        );

        // PRODUCTS
        var products = productsRepository.saveAll(
                List.of(
                        new Product(null, categories.get(0).getId(), "Laptop", "Laptop", 1000.0, new Inventory(null, null, 10)),
                        new Product(null, categories.get(1).getId(), "T-Shirt", "T-Shirt", 10.0, new Inventory(null, null, 100)),
                        new Product(null, categories.get(2).getId(), "Java Book", "Java Book", 20.0, new Inventory(null, null, 50))
                )
        );
        assertAll(
                () -> products.forEach(System.out::println),
                /*
                    Product[id=2, category=1, name=Laptop, description=Laptop, price=1000.0, inventory=Inventory[id=2, productId=null, quantity=10]]
                    Product[id=3, category=2, name=T-Shirt, description=T-Shirt, price=10.0, inventory=Inventory[id=3, productId=null, quantity=100]]
                    Product[id=4, category=3, name=Java Book, description=Java Book, price=20.0, inventory=Inventory[id=4, productId=null, quantity=50]]
                 */
                () -> productsRepository.findAll().forEach(System.out::println),
                /*
                    Product[id=1, category=2, name=Blazer, description=Suits, price=10.0, inventory=Inventory[id=1, productId=1, quantity=100]]
                    Product[id=2, category=1, name=Laptop, description=Laptop, price=1000.0, inventory=Inventory[id=2, productId=2, quantity=10]]
                    Product[id=3, category=2, name=T-Shirt, description=T-Shirt, price=10.0, inventory=Inventory[id=3, productId=3, quantity=100]]
                    Product[id=4, category=3, name=Java Book, description=Java Book, price=20.0, inventory=Inventory[id=4, productId=4, quantity=50]]
                 */
                () -> productsRepository.findById(1L).ifPresent(System.out::println),
                /*
                    Product[id=1, category=2, name=Blazer, description=Suits, price=10.0, inventory=Inventory[id=1, productId=1, quantity=100]]
                 */
                () -> productsRepository.findByCategory(2L).forEach(System.out::println),
                /*
                    Product[id=1, category=2, name=Blazer, description=Suits, price=10.0, inventory=Inventory[id=1, productId=1, quantity=100]]
                    Product[id=3, category=2, name=T-Shirt, description=T-Shirt, price=10.0, inventory=Inventory[id=3, productId=3, quantity=100]]
                */
                () -> {
                    System.out.println("#####################");
                    productsRepository.findProductByCategoryAndId(2L, 1L).ifPresent(System.out::println);
                    System.out.println("#####################");

                }
                //Product(id=1, category=2, name=Blazer, description=Suits, price=10.0, inventory=Inventory(id=1, productId=1, quantity=100))
        );

        //CART
        var carts = cartRepository.saveAll(
                List.of(
                        new Cart(null, 1L, Set.of(
                                new CartProductRef(null, products.get(0).getId(), 2),
                                new CartProductRef(null, products.get(1).getId(), 3)
                        )),
                        new Cart(null, 2L, Set.of(
                                new CartProductRef(null, products.get(1).getId(), 1),
                                new CartProductRef(null, products.get(2).getId(), 2)
                        ))
                )
        );
        assertAll(
                () -> carts.forEach(System.out::println),
                /*
                    Cart[id=1, userId=1, cartItems=[CartProductRef[cartId=null, productId=2, quantity=2], CartProductRef[cartId=null, productId=3, quantity=3]]]
                    Cart[id=2, userId=2, cartItems=[CartProductRef[cartId=null, productId=3, quantity=1], CartProductRef[cartId=null, productId=4, quantity=2]]]
                */
                () -> cartRepository.findAll().forEach(System.out::println)
                /*
                    Cart[id=1, userId=1, cartItems=[CartProductRef[cartId=1, productId=2, quantity=2], CartProductRef[cartId=1, productId=3, quantity=3]]]
                    Cart[id=2, userId=2, cartItems=[CartProductRef[cartId=2, productId=3, quantity=1], CartProductRef[cartId=2, productId=4, quantity=2]]]
                */
        );

        //ORDER
        var orders = orderRepository.saveAll(
                List.of(
                        new Order(null, 1L, Order.OrderStatus.PENDING, Set.of(
                                new OrderProductRef(null, products.get(0).getId(), 2)
                        )),
                        new Order(null, 1L, Order.OrderStatus.PENDING, Set.of(
                                new OrderProductRef(null, products.get(1).getId(), 3),
                                new OrderProductRef(null, products.get(2).getId(), 1)
                        )),
                        new Order(null, 2L, Order.OrderStatus.PENDING, Set.of(
                                new OrderProductRef(null, products.get(2).getId(), 2)
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
                () -> orderRepository.findAll().forEach(System.out::println),
                /*
                    Order[id=1, userId=1, status=PENDING, products=[OrderProductRef[orderId=1, productId=2, quantity=2]]]
                    Order[id=2, userId=1, status=PENDING, products=[OrderProductRef[orderId=2, productId=3, quantity=3], OrderProductRef[orderId=2, productId=4, quantity=1]]]
                    Order[id=3, userId=2, status=PENDING, products=[OrderProductRef[orderId=3, productId=4, quantity=2]]]
                */
                () -> orderRepository.findById(1L).ifPresent(System.out::println),
                /*
                    Order[id=1, userId=1, status=PENDING, products=[OrderProductRef[orderId=1, productId=2, quantity=2]]]
                */
                () -> orderRepository.findByUserId(1L).forEach(System.out::println)
                /*
                    Order[id=1, userId=1, status=PENDING, products=[OrderProductRef[orderId=1, productId=2, quantity=2]]]
                    Order[id=2, userId=1, status=PENDING, products=[OrderProductRef[orderId=2, productId=3, quantity=3], OrderProductRef[orderId=2, productId=4, quantity=1]]]
                */
        );

        //ORDER - UPDATE

        //FAVORITE
        var favorites = favoriteRepository.saveAll(
                List.of(
                        new Favorite(null, 1L, Set.of()),
                        new Favorite(null, 1L, Set.of()),
                        new Favorite(null, 2L, Set.of(
                                new FavoriteProductRef(null, products.get(0).getId()),
                                new FavoriteProductRef(null, products.get(1).getId())
                        ))
                )
        );

        assertAll(
                () -> favorites.forEach(System.out::println),
                /*
                    Favorite[id=1, userId=1, products=[]]
                    Favorite[id=2, userId=2, products=[FavoriteProductRef[favoriteId=null, productId=2], FavoriteProductRef[favoriteId=null, productId=3]]]
                */
                () -> favoriteRepository.findAll().forEach(System.out::println)
                /*
                    Favorite[id=1, userId=1, products=[]]
                    Favorite[id=2, userId=2, products=[FavoriteProductRef[favoriteId=2, productId=2], FavoriteProductRef[favoriteId=2, productId=3]]]
                */
        );

    }

}
