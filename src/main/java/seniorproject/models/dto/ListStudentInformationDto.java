package seniorproject.models.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;


@Data
public class ListStudentInformationDto {
        private List<StudentInformationDto> students;
        private String currentStudentUsername;
}
