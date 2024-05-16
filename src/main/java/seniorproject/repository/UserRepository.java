package seniorproject.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import seniorproject.models.concretes.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}