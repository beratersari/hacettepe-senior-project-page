package seniorproject.business.concretes;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import seniorproject.business.abstracts.UserService;
import seniorproject.core.utilities.results.DataResult;
import org.springframework.security.core.userdetails.User;
import seniorproject.core.utilities.results.SuccessDataResult;
import seniorproject.dataAccess.abstracts.ProfessorDao;
import seniorproject.dataAccess.abstracts.StudentDao;
import seniorproject.models.concretes.Professor;
import seniorproject.models.concretes.Student;
import seniorproject.models.dto.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserManager implements UserService {

    ProfessorDao professorDao;
    StudentDao studentDao;

    public UserManager(ProfessorDao professorDao, StudentDao studentDao) {
        this.professorDao = professorDao;
        this.studentDao = studentDao;
    }

    @Override
    public DataResult<User> profile(){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        User userDetails = (User) authentication.getPrincipal();
        return new SuccessDataResult<>(userDetails, "User profile listed.");

    }

    @Override
    public DataResult<List<ProfessorInformationDto>> getProfessorsWithId(ProfessorWithIdRequestDto professorWithIdRequestDto) {
        List<UUID> professorIds = professorWithIdRequestDto.getProfessorIds();
        UUID requestedUsersId = professorWithIdRequestDto.getRequestedUserId();

        List<ProfessorInformationDto> professorInformationResponseDtos = new ArrayList<>();
        for(UUID professorId : professorIds){
            Professor professor = professorDao.getProfessorById(professorId);
            if(professor == null){
                return new SuccessDataResult<>(null, "Professor not found.");
            }
            if(professor.getId().equals(requestedUsersId)){
                continue;
            }
            ProfessorInformationDto professorInformationResponseDto = new ProfessorInformationDto();
            professorInformationResponseDto.setId(professor.getId());
            professorInformationResponseDto.setUsername(professor.getUsername());
        }
        return new SuccessDataResult<>(professorInformationResponseDtos, "Professors listed.");
    }

    @Override
    public DataResult<ListProfessorsInformationsDto> getProfessors(UUID sessionId) {
        List<Professor> professors = professorDao.getAll();
        List<ProfessorInformationDto> professorInformationResponseDtos = new ArrayList<>();
        String currName = "";
        for(Professor professor : professors){
            if (professor.getId().equals(sessionId)){
                currName = professor.getUsername();
                continue;
            }
            ProfessorInformationDto professorInformationResponseDto = new ProfessorInformationDto();
            professorInformationResponseDto.setId(professor.getId());
            professorInformationResponseDto.setUsername(professor.getUsername());
            professorInformationResponseDtos.add(professorInformationResponseDto);
        }

        ListProfessorsInformationsDto listProfessorsInformationsDto = new ListProfessorsInformationsDto();
        listProfessorsInformationsDto.setProfessors(professorInformationResponseDtos);
        listProfessorsInformationsDto.setCurrentProfessorUsername(currName);

        return new SuccessDataResult<>(listProfessorsInformationsDto, "Professors listed.");
    }

    @Override
    public DataResult<ListStudentInformationDto> getStudents(UUID sessionId) {
        List<Student> students = studentDao.getAll();
        List<StudentInformationDto> studentInformationResponseDtos = new ArrayList<>();
        String currName = "";
        for(Student student : students){
            if (student.getId().equals(sessionId)){
                currName = student.getUsername();
                continue;
            }
            StudentInformationDto studentInformationResponseDto = new StudentInformationDto();
            studentInformationResponseDto.setId(student.getId());
            studentInformationResponseDto.setUsername(student.getUsername());
            studentInformationResponseDtos.add(studentInformationResponseDto);
        }

        ListStudentInformationDto listStudentInformationDto = new ListStudentInformationDto();
        listStudentInformationDto.setStudents(studentInformationResponseDtos);
        listStudentInformationDto.setCurrentStudentUsername(currName);

        return new SuccessDataResult<>(listStudentInformationDto, "Students listed.");

    }
}
