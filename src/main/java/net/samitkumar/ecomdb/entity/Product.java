package net.samitkumar.ecomdb.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

//Category Entity can be pull with this way - @ReadOnlyProperty @MappedCollection(idColumn = "id", keyColumn = "category") Category category
@Table("products")
public record Product(@Id Long id, Long category, String name, String description, Double price, Integer quantity) {
}
