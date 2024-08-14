package net.samitkumar.ecomdb.repository;

import net.samitkumar.ecomdb.entity.Inventory;
import org.springframework.data.repository.ListCrudRepository;

/*
Summary of When to Update Inventory:
    Best Practice: Update inventory during order placement.

Pros: Ensures inventory accurately reflects purchased products, reduces the risk of overselling, and keeps inventory management straightforward.
Cons: Requires robust handling of concurrent orders to avoid race conditions.

Alternative: Update inventory during order processing.

Pros: Suitable for complex order processing systems where inventory is reserved during fulfillment.
Cons: Potential for overselling if multiple orders are processed simultaneously before inventory is checked.

Additional Considerations:
Concurrency Management: Implement locking mechanisms or atomic transactions to ensure that two users can't simultaneously purchase the last item in stock.
Stock Reservations: If necessary, implement a reservation system where stock is reserved when an order is placed but only finalized during processing.

**/
public interface InventoryRepository extends ListCrudRepository<Inventory, Long> {
}
