package seniorproject.business.abstracts;

import seniorproject.core.utilities.results.DataResult;
import seniorproject.models.dto.ApplicationDto;

import java.util.List;

public interface ApplicationService {

    DataResult<List<ApplicationDto>> getApplicationsByProfessorId(Long professorId);
}
