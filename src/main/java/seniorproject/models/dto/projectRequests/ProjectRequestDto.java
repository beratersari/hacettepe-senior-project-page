package seniorproject.models.dto.projectRequests;

import lombok.Data;

import java.util.UUID;

@Data
public class ProjectRequestDto {

    UUID sessionId;
    ProjectSearchDto search;
    ProjectSortDto sort;
    int pageNumber;
    int pageSize;


}
