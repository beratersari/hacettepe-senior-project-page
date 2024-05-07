package seniorproject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import seniorproject.models.concretes.Application;

import java.util.UUID;

public interface ApplicationDao extends JpaRepository<Application, UUID> {
}
