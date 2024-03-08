package seniorproject.models.dto;
import java.util.List;
import lombok.Data;

@Data
public class ProfessorDto {
    private long id;
    private String name;
    private String email;
    private String password;
    private List<Long> projectIds;
}
