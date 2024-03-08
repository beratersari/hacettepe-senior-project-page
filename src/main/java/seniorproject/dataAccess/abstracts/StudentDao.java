package seniorproject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import seniorproject.models.concretes.Student;

public interface StudentDao extends JpaRepository<Student, Long> {
}
