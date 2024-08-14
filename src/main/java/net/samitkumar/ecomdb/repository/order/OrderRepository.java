package net.samitkumar.ecomdb.repository.order;

import net.samitkumar.ecomdb.entity.order.Order;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface OrderRepository extends ListCrudRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}
