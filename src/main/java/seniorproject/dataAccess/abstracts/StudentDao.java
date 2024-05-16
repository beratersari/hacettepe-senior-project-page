package seniorproject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import seniorproject.models.concretes.Student;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentDao extends JpaRepository<Student, UUID> {
    @Query("SELECT s FROM Student s")
    List<Student> getAll();

    @Query("SELECT s FROM Student s WHERE s.id = :studentId")
    Optional<Student> findById(UUID studentId);

}
