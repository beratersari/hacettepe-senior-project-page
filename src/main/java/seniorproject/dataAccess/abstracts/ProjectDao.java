package seniorproject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import seniorproject.models.concretes.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProjectDao extends JpaRepository<Project, Long>{

    List<Project> findAllByGroup_Id(Long groupId); // Onemli ozellik !!!

    @Query(value = "SELECT p FROM Project p where lower(p.name) like %:name%")
    List<Project> findAllByNameLikeIgnoreCase(String name);

    @Query(value =
            "SELECT DISTINCT p " +
                    "FROM Project p " +
                    "LEFT JOIN p.professors pr " +
                    "LEFT JOIN p.group.students s " +
                    "WHERE LOWER(pr.username) LIKE %:authorName% OR LOWER(s.username) LIKE %:authorName%"
    )
    List<Project> findAllByAuthorNameContaining(@Param("authorName") String authorName);


}

