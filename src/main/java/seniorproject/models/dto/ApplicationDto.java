package seniorproject.models.dto;
import seniorproject.models.concretes.enums.EStatus;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ApplicationDto {
    private UUID id;
    private ProjectDto project;
    private UUID groupId;
    private EStatus status;
    private List<StudentInformationDto> groupMembers;
    private String groupName;

}
