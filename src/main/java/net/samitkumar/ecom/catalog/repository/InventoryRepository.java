package net.samitkumar.ecom.catalog.repository;

import net.samitkumar.ecom.catalog.entity.Inventory;
import org.springframework.data.repository.ListCrudRepository;
public interface InventoryRepository extends ListCrudRepository<Inventory, Long> {
}
