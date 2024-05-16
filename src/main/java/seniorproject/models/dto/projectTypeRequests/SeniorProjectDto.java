package seniorproject.models.dto.projectTypeRequests;

import lombok.Data;
import seniorproject.models.concretes.enums.EProjectTypeStatus;
import seniorproject.models.dto.ProjectDto;

import java.util.List;
import java.util.UUID;

@Data
public class SeniorProjectDto {
    private UUID id;
    private String name;
    private String term;
    private EProjectTypeStatus activeness;
    private List<String> timelines;
    private List<ProjectDto> projects;
}
