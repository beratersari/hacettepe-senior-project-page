package seniorproject.models.dto;

import lombok.Data;

@Data
public class ProjectRequestDto {

    ProjectSearchDto search;
    ProjectSortDto sort;

    int pageNo;
    int pageSize;


}
