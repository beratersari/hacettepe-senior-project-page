package seniorproject.models.dto.projectRequests;

import lombok.Data;

@Data
public class ProjectRequestDto {

    Long sessionId;
    ProjectSearchDto search;
    ProjectSortDto sort;

    int pageNo;
    int pageSize;


}
