package seniorproject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import seniorproject.core.utilities.results.DataResult;
import seniorproject.models.concretes.ProjectType;
import seniorproject.models.concretes.SeniorProject;

import java.util.List;

public interface ProjectTypeDao extends JpaRepository<ProjectType, Long> {

    @Query(value = "SELECT p FROM ProjectType p WHERE p.activeness = 'ACTIVE'")
    List<ProjectType> findByActiveness();

    @Query(value = "SELECT p FROM ProjectType p LEFT JOIN SeniorProject sp ON p.id = sp.id WHERE sp.term = :term")
    ProjectType findByTerm(String term);

}
