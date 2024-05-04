package seniorproject.models.dto.projectRequests;

import lombok.Data;
import seniorproject.models.dto.EType;

@Data
public class ProjectSearchDto {
    private EType type;
    private String value;
}
