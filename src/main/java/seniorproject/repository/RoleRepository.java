package seniorproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seniorproject.models.concretes.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);


}
