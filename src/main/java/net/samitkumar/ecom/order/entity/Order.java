package net.samitkumar.ecom.order.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

@Table("orders")
@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class Order {
    @Id
    private Long id;
    private Long userId;
    private OrderStatus status;
    @MappedCollection(idColumn = "order_id")
    private Set<OrderProductRef> products;

    public enum OrderStatus {
        PENDING,
        SHIPPED,
        DELIVERED
    }
}