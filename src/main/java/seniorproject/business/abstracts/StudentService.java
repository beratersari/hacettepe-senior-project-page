package seniorproject.business.abstracts;

import seniorproject.core.utilities.results.DataResult;
import seniorproject.models.concretes.Student;

import java.util.List;

public interface StudentService{
    DataResult<List<Student>> getAll();
}
