package seniorproject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import seniorproject.models.concretes.Project;
import seniorproject.models.concretes.Student;

import java.util.List;

public interface StudentDao extends JpaRepository<Student, Long > {
}
