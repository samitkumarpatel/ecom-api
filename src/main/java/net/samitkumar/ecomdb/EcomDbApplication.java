package net.samitkumar.ecomdb;

import net.samitkumar.ecomdb.entity.Category;
import net.samitkumar.ecomdb.entity.Inventory;
import net.samitkumar.ecomdb.entity.Product;
import net.samitkumar.ecomdb.entity.User;
import net.samitkumar.ecomdb.entity.cart.Cart;
import net.samitkumar.ecomdb.entity.favorite.Favorite;
import net.samitkumar.ecomdb.entity.order.Order;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@SpringBootApplication
public class EcomDbApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcomDbApplication.class, args);
	}

	@Bean
	RepositoryRestConfigurer repositoryRestConfigurer() {
		return RepositoryRestConfigurer.withConfig(config -> {
			config.exposeIdsFor(User.class, Category.class, Product.class, Inventory.class, Cart.class, Order.class, Favorite.class);
		});
	}
}

