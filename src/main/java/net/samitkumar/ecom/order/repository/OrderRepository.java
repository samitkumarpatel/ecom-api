package net.samitkumar.ecom.order.repository;

import net.samitkumar.ecom.order.entity.Order;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface OrderRepository extends ListCrudRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}
