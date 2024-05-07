package seniorproject.business.abstracts;

import seniorproject.core.utilities.results.DataResult;
import seniorproject.models.dto.ApplicationDto;

import java.util.List;
import java.util.UUID;

public interface ApplicationService {

    DataResult<List<ApplicationDto>> getApplicationsByProfessorId(UUID professorId);
}
