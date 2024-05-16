package seniorproject.models.dto.projectRequests;

import lombok.Data;
import seniorproject.models.dto.ProfessorInformationDto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class ProjectCreateDto {
    private UUID sessionId;
    private String title;
    private String term;
    private String description;
    private List<ProfessorInformationDto> professors;
    private UUID projectTypeId;
    private Long studentLimit;
    private List<String> keywords = new ArrayList<>();
}
