package seniorproject.business.abstracts;

import org.springframework.data.domain.Sort;
import seniorproject.core.utilities.results.DataResult;
import seniorproject.models.dto.EType;
import seniorproject.models.dto.ProjectDto;

import java.util.List;

public interface ProjectService {
    DataResult<List<ProjectDto>> searchAndSortProjects(EType searchType, String searchTerm, String sortType, Sort.Direction sortDirection, int pageNumber, int pageSize);
}
