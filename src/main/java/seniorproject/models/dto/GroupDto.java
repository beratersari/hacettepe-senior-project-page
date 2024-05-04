package seniorproject.models.dto;
import java.util.List;

import lombok.Data;

@Data
public class GroupDto {
    private long id;
    private String groupName;
    private List<String> groupMembers;
    private List<ApplicationDto> applications;
    private List<Long> projectIds;
}
