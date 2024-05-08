package seniorproject.models.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class TimelineDto {
    private UUID id;
    private String deliveryDate;
    private String deliveryName;
    private UUID projectTypeId;
}
