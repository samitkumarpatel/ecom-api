package net.samitkumar.ecom.admin.repository;

import net.samitkumar.ecom.admin.entity.User;
import org.springframework.data.repository.ListCrudRepository;

public interface UserRepository extends ListCrudRepository<User, Long> {
}
