package seniorproject.models.dto.projectTypeRequests;

import lombok.Data;

import java.util.List;

@Data
public class CreateProjectTypeDto {
    private String name;
    private String activeness;
    private List<String> timelines;

}
