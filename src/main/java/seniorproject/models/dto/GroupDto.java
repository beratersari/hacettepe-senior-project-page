package seniorproject.models.dto;
import java.util.List;
import lombok.Data;

@Data
public class GroupDto {
    private long id;
    private List<Long> studentIds;
    private List<Long> applicationIds;
    private List<Long> projectIds;
}
