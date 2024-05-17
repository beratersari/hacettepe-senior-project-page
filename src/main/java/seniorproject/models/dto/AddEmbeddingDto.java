package seniorproject.models.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class AddEmbeddingDto {
    private UUID projectId;
    private String embedding;
}
