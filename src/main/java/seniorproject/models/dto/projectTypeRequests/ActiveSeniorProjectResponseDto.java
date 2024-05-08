package seniorproject.models.dto.projectTypeRequests;

import lombok.Data;

import java.util.UUID;

@Data
public class ActiveSeniorProjectResponseDto {
    private UUID id;
    private String name;
    private String term;
}
