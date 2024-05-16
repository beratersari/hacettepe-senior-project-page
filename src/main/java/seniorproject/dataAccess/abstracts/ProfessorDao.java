package seniorproject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import seniorproject.models.concretes.Professor;

import java.util.List;
import java.util.UUID;

public interface ProfessorDao extends JpaRepository<Professor, UUID> {
    Professor getProfessorById(UUID userId);

    @Query("SELECT p FROM Professor p")
    List<Professor> getAll();
}
