package seniorproject.models.dto.projectRequests;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProjectCreateDto {
    private Long sessionId;
    private String title;
    private String term;
    private String description;
    private List<Long> professorIds;
    private List<Long> applicationIds;
    private Long ProjectTypeId;
    private List<String> keywords = new ArrayList<>();
}
