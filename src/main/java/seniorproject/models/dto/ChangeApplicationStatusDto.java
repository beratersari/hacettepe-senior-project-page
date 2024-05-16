package seniorproject.models.dto;

import lombok.Data;
import seniorproject.models.concretes.enums.EStatus;

import java.util.UUID;

@Data
public class ChangeApplicationStatusDto {
    private UUID id;
    private EStatus status;
}
