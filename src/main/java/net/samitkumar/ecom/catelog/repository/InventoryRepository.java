package net.samitkumar.ecom.catelog.repository;

import net.samitkumar.ecom.catelog.entity.Inventory;
import org.springframework.data.repository.ListCrudRepository;
public interface InventoryRepository extends ListCrudRepository<Inventory, Long> {
}
