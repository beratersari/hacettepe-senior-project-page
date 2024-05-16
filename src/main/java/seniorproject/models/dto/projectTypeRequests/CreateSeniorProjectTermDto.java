package seniorproject.models.dto.projectTypeRequests;

import lombok.Data;
import seniorproject.models.dto.TimelineDto;

import java.util.List;

@Data
public class CreateSeniorProjectTermDto {
    private String name;
    private String term;
    private List<TimelineDto> timelines;
}
