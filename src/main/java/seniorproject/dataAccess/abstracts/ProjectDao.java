package seniorproject.dataAccess.abstracts;

import seniorproject.models.concretes.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectDao extends JpaRepository<Project, Long>{

}
