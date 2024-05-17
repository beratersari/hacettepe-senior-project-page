package seniorproject.models.dto.projectRequests;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ProjectIdsDto {
    private List<UUID> projectIds;
}
