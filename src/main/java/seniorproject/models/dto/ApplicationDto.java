package seniorproject.models.dto;
import seniorproject.models.concretes.enums.Status;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ApplicationDto {
    private UUID id;
    private UUID projectId;
    private UUID groupId;
    private Status status;
    private List<String> groupMembers;
    private String projectTitle;
}
