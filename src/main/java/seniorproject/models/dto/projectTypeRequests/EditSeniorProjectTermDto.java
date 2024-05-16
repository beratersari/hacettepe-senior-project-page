package seniorproject.models.dto.projectTypeRequests;

import lombok.Data;
import seniorproject.models.dto.TimelineDto;

import java.util.List;
import java.util.UUID;

@Data
public class EditSeniorProjectTermDto {
    private UUID id;
    private String name;
    private String term;
    private List<TimelineDto> timelines;
}
