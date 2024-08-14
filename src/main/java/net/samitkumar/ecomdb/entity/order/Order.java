package net.samitkumar.ecomdb.entity.order;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

@Table("orders")
public record Order(@Id Long id, Long userId, OrderStatus status, @MappedCollection(idColumn = "order_id") Set<OrderProductRef> products) {

    public enum OrderStatus {
        PENDING,
        SHIPPED,
        DELIVERED
    }
}
