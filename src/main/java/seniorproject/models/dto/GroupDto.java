package seniorproject.models.dto;
import java.util.List;

import lombok.Data;

@Data
public class GroupDto {
    private String id;
    private String groupName;
    private List<String> groupMembers;
    private List<ApplicationDto> applications;
    private List<String> projectIds;
}
