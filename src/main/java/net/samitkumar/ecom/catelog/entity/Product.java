package net.samitkumar.ecom.catelog.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

//Category Entity can be pull with this way - @ReadOnlyProperty @MappedCollection(idColumn = "id", keyColumn = "category") Category category
@Table("products")
@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class Product {
    @Id
    private Long id;
    private Long category;
    private String name;
    private String description;
    private Double price;
    @MappedCollection(idColumn = "product_id")
    private Inventory inventory;
}