package seniorproject.dataAccess.abstracts;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import seniorproject.models.concretes.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProjectDao extends JpaRepository<Project, UUID>{

    @Query(value =
            "SELECT DISTINCT p " +
                    "FROM Project p " +
                    "LEFT JOIN p.professors pr " +
                    "LEFT JOIN p.group.students s " +
                    "WHERE (LOWER(pr.username) LIKE %:authorName% OR LOWER(s.username) LIKE %:authorName%) AND (p.EProjectStatus = 'WORKING' OR p.EProjectStatus = 'ARCHIVED')"
    )
    Page<Project> findByAuthorNameContainingIgnoreCase(@Param("authorName") String authorName, Pageable pageable);

    @Query(value = "SELECT p FROM Project p WHERE lower(p.title) like %:title% and (p.EProjectStatus = 'WORKING' OR p.EProjectStatus = 'ARCHIVED')")
    Page<Project> findByTitleContainingIgnoreCase(@Param("title") String title, Pageable pageable);

    @Query(value = "SELECT p FROM Project p LEFT JOIN p.keywords k WHERE lower(k.name) like %:keyword% and (p.EProjectStatus = 'WORKING' OR p.EProjectStatus = 'ARCHIVED')")
    Page<Project> findByKeywordsContainingIgnoreCase(@Param("keyword") String keyword, Pageable pageable);

    @Query(value = "SELECT p FROM Project p LEFT JOIN SeniorProject sp ON p.projectType.id = sp.id WHERE sp.term like %:searchTerm%")
    Page<Project> findByProjectTypeContainingIgnoreCase(@Param("searchTerm") String searchTerm, Pageable pageable);

    List<Project> findAllByGroupId(UUID group_id);

    List<Project> findAllByProfessorsId(UUID professor_id);

    @Query(value = "SELECT p FROM Project p WHERE p.id in :projectIds ")
    List<Project> findAllByProjectIds(@Param("projectIds") List<UUID> projectIds);

}