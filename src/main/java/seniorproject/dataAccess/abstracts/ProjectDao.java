package seniorproject.dataAccess.abstracts;

import seniorproject.models.concretes.Application;
import seniorproject.models.concretes.Group;
import seniorproject.models.concretes.Professor;
import seniorproject.models.concretes.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectDao extends JpaRepository<Project, Long>{

    List<Project> findAllByIsWorking(boolean b);
}
