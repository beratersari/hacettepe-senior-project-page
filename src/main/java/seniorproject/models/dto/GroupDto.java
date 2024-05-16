package seniorproject.models.dto;
import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class GroupDto {
    private UUID id;
    private String groupName;
    private List<StudentInformationDto> groupMembers;
    private List<ApplicationDto> applications;
    private List<ProjectDto> projects;
}
