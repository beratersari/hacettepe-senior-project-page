package seniorproject.models.dto.projectRequests;

import lombok.Data;

@Data
public class ProjectWithTypesRequestDto {
    private Long sessionId;
    private String searchTerm;
    private int pageNumber;
    private int pageSize;
}
