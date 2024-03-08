package seniorproject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import seniorproject.models.concretes.Application;

public interface ApplicationDao extends JpaRepository<Application, Long> {
}
