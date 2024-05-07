package seniorproject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import seniorproject.models.concretes.Role;

import java.util.UUID;


public interface RoleDao extends JpaRepository<Role, UUID> {
}
