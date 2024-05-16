package seniorproject.models.dto.projectRequests;

import lombok.Data;
import seniorproject.models.dto.ProfessorInformationDto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class ProjectUpdateDto{
    private UUID id;
    private UUID sessionId;
    private String title;
    private String description;
    private List<ProfessorInformationDto> professors = new ArrayList<>();
    private List<String> keywords = new ArrayList<>();
    private Long studentLimit;
    private UUID GroupId;
}
