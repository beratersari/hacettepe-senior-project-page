package seniorproject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import seniorproject.models.concretes.Professor;

public interface ProfessorDao extends JpaRepository<Professor, Long> {
}
