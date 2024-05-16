package seniorproject.models.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ProfessorWithIdRequestDto {
    private UUID requestedUserId;
    private List<UUID> professorIds;
}
