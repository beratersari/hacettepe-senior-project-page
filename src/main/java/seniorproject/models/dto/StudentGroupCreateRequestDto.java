package seniorproject.models.dto;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class StudentGroupCreateRequestDto {
    private UUID sessionId;
    private String groupName;
    private List<StudentInformationDto> groupMembers;
}
