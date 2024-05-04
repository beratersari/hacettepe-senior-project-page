package seniorproject.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import seniorproject.business.abstracts.StudentService;
import seniorproject.core.utilities.results.DataResult;
import seniorproject.models.concretes.Project;
import seniorproject.models.concretes.Student;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentsController {

    private StudentService studentService;

    @Autowired
    public StudentsController(StudentService studentService) {
        super();
        this.studentService = studentService;
    }

    @GetMapping("/getall")
    public DataResult<List<Student>> getAll() {
        return this.studentService.getAll();
    }


}
