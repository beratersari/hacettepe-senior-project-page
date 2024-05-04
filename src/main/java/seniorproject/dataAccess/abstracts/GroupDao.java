package seniorproject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import seniorproject.models.concretes.Group;

import java.util.List;

public interface GroupDao extends JpaRepository<Group, Long>{

    @Query("SELECT g FROM Group g JOIN g.students s WHERE s.id = :studentId")
    List<Group> findAllByStudentId(@Param("studentId") Long studentId);


}
