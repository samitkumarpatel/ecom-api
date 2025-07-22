package net.samitkumar.ecom.catalog.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("inventory")
@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class Inventory {
    @Id
    private Long id;
    private Long productId;
    private Integer quantity;
}


