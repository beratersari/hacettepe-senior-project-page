package seniorproject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import seniorproject.models.concretes.Role;


public interface RoleDao extends JpaRepository<Role, Long> {
}
