package net.samitkumar.ecom.catalog.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

@Table("categories")
@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class Category {
    @Id
    private Long id;
    private String name;
    private String description;
    @MappedCollection(idColumn = "category")
    private Set<Product> products;
}
