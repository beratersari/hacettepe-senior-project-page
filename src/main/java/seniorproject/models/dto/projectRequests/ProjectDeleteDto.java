package seniorproject.models.dto.projectRequests;

import lombok.Data;

import java.util.UUID;

@Data
public class ProjectDeleteDto {
    private UUID sessionId;
    private UUID projectId;
}
