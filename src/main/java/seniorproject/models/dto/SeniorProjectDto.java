package seniorproject.models.dto;

import lombok.Data;
import seniorproject.models.concretes.enums.EProjectTypeStatus;

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
