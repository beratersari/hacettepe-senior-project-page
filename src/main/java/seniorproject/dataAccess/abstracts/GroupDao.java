package seniorproject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import seniorproject.models.concretes.Group;

import java.util.List;
import java.util.UUID;

public interface GroupDao extends JpaRepository<Group, UUID>{

    @Query("SELECT g FROM Group g JOIN g.students s WHERE s.id = :studentId")
    List<Group> findAllByStudentId(@Param("studentId") UUID studentId);


}
