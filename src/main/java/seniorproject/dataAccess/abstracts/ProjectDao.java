package seniorproject.dataAccess.abstracts;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import seniorproject.models.concretes.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProjectDao extends JpaRepository<Project, Long>{

    @Query(value =
            "SELECT DISTINCT p " +
                    "FROM Project p " +
                    "LEFT JOIN p.professors pr " +
                    "LEFT JOIN p.group.students s " +
                    "WHERE LOWER(pr.username) LIKE %:authorName% OR LOWER(s.username) LIKE %:authorName%"
    )
    Page<Project> findByAuthorNameContainingIgnoreCase(@Param("authorName") String authorName, Pageable pageable);

    @Query(value = "SELECT p FROM Project p WHERE lower(p.title) like %:title%")
    Page<Project> findByTitleContainingIgnoreCase(@Param("title") String title, Pageable pageable);

    @Query(value = "SELECT p FROM Project p LEFT JOIN p.keywords k WHERE lower(k.name) like %:keyword%")
    Page<Project> findByKeywordsContainingIgnoreCase(@Param("keyword") String keyword, Pageable pageable);
}