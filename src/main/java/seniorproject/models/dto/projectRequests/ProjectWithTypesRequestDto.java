package seniorproject.models.dto.projectRequests;

import lombok.Data;

import java.util.UUID;

@Data
public class ProjectWithTypesRequestDto {
    private UUID sessionId;
    private String searchTerm;
    private int pageNumber;
    private int pageSize;
}
