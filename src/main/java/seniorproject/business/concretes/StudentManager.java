package seniorproject.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seniorproject.business.abstracts.StudentService;
import seniorproject.core.utilities.results.DataResult;
import seniorproject.core.utilities.results.SuccessDataResult;
import seniorproject.dataAccess.abstracts.GroupDao;
import seniorproject.dataAccess.abstracts.StudentDao;
import seniorproject.models.concretes.Student;

import java.util.List;

@Service
public class StudentManager implements StudentService {

    private StudentDao studentDao;
    private GroupDao groupDao;

    @Autowired
    public StudentManager(StudentDao studentDao) {
        super();
        this.studentDao = studentDao;
    }
    @Override
    public DataResult<List<Student>> getAll() {
        return new SuccessDataResult<List<Student>>(this.studentDao.findAll(),"Students listed.");
    }

}
