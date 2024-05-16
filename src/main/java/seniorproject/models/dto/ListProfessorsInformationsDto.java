package seniorproject.models.dto;

import lombok.Data;

import java.util.List;

@Data
public class ListProfessorsInformationsDto {
    private List<ProfessorInformationDto> professors;
    private String currentProfessorUsername;

}
