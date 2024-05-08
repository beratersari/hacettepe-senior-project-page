package seniorproject.models.dto.projectTypeRequests;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ProjectTypeDto {
    private UUID id;
    private String name;
    private String activeness;
    private List<String> timelines;

}

