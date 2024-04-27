package seniorproject.models.dto;

import lombok.Data;

@Data
public class ProjectRequestDto {

    ProjectSearchDto projectSearchDto;
    ProjectSortDto projectSortDto;

    int pageNo;
    int pageSize;


}
