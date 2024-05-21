package seniorproject.models.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class UpdateGroupDto {
    private UUID GroupId;
    private UUID sessionId;
    private String groupName;
    private List<StudentInformationDto> groupMembers;


}
