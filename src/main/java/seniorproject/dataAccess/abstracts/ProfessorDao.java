package seniorproject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import seniorproject.models.concretes.Professor;

import java.util.UUID;

public interface ProfessorDao extends JpaRepository<Professor, UUID> {
}
