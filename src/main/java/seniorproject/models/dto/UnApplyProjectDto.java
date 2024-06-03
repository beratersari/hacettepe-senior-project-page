package seniorproject.models.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UnApplyProjectDto {
    private UUID projectId;
    private UUID studentId;
}
