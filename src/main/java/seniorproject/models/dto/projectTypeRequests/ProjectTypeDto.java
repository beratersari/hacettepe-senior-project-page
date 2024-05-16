package seniorproject.models.dto.projectTypeRequests;

import lombok.Data;
import seniorproject.models.dto.TimelineDto;

import java.util.List;
import java.util.UUID;

@Data
public class ProjectTypeDto {
    private UUID id;
    private String name;
    private String activeness;
    private List<TimelineDto> timelines;
}

