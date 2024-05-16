package seniorproject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import seniorproject.models.concretes.Application;

import java.util.List;
import java.util.UUID;

public interface ApplicationDao extends JpaRepository<Application, UUID> {

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN TRUE ELSE FALSE END FROM Application a WHERE a.project.id = :projectId AND a.group.id = :groupId")
    boolean isApplied(@Param("projectId") UUID projectId,@Param("groupId") UUID groupId);

    @Query("SELECT a FROM Application a LEFT JOIN a.project p LEFT JOIN p.professors pr WHERE pr.id = :professorId")
    List<Application> findAllByProjectProfessorId(UUID professorId);

    @Query("SELECT a FROM Application a LEFT JOIN a.project p WHERE p.id = :projectId")
    List<Application> findAllByProjectId(UUID projectId);
}
