package seniorproject.core.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;
import seniorproject.core.entities.User;

public interface UserDao extends JpaRepository<User, Long> {
}
