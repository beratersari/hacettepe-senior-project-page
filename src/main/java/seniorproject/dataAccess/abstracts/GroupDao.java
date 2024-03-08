package seniorproject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import seniorproject.models.concretes.Group;

public interface GroupDao extends JpaRepository<Group, Long>{
}
