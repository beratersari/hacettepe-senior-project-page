package seniorproject.models.dto;

import lombok.Data;
import seniorproject.models.concretes.enums.EStatus;

import java.util.List;
import java.util.UUID;

@Data
public class GroupApplicationDto {
    private UUID groupId;
    private UUID applicationId;
    private String groupName;
    private EStatus applicationStatus;
    private List<StudentInformationDto> groupMembers;

}
