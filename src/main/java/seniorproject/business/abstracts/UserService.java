package seniorproject.business.abstracts;

import seniorproject.core.utilities.results.DataResult;
import org.springframework.security.core.userdetails.User;
import seniorproject.models.dto.ListProfessorsInformationsDto;
import seniorproject.models.dto.ListStudentInformationDto;
import seniorproject.models.dto.ProfessorInformationDto;
import seniorproject.models.dto.ProfessorWithIdRequestDto;

import java.util.List;
import java.util.UUID;

public interface UserService {

    DataResult<User> profile();

    DataResult<List<ProfessorInformationDto>> getProfessorsWithId(ProfessorWithIdRequestDto professorWithIdRequestDto);

    DataResult<ListProfessorsInformationsDto> getProfessors(UUID sessionId);

    DataResult<ListStudentInformationDto> getStudents(UUID sessionId);
}
