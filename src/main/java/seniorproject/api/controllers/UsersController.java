package seniorproject.api.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import seniorproject.business.abstracts.UserService;
import seniorproject.core.utilities.results.DataResult;
import org.springframework.security.core.userdetails.User;
import seniorproject.models.dto.ListProfessorsInformationsDto;
import seniorproject.models.dto.ListStudentInformationDto;
import seniorproject.models.dto.ProfessorInformationDto;
import seniorproject.models.dto.ProfessorWithIdRequestDto;

import java.util.List;
import java.util.UUID;


@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/users")

public class UsersController {
    private final UserService userService;

    public UsersController(UserService userService) {
        super();
        this.userService = userService;
    }


    @GetMapping("/getProfile")
    public DataResult<User> profile() {
        return this.userService.profile();

    }

    @PostMapping("/getProfessorsWithId")
    @PreAuthorize("hasAnyRole('ROLE_STUDENT', 'ROLE_PROFESSOR')")
    public DataResult<List<ProfessorInformationDto>> getProfessorsWithId(@RequestBody ProfessorWithIdRequestDto professorWithIdRequestDto) {
        return this.userService.getProfessorsWithId(professorWithIdRequestDto);
    }

    @PostMapping("/getProfessors")
    @PreAuthorize("hasAnyRole('ROLE_STUDENT', 'ROLE_PROFESSOR')")
    public DataResult<ListProfessorsInformationsDto> getProfessors(@RequestBody UUID sessionId) {
        return this.userService.getProfessors(sessionId);
    }

    @PostMapping("/getStudents")
    @PreAuthorize("hasAnyRole('ROLE_STUDENT', 'ROLE_PROFESSOR')")
    public DataResult<ListStudentInformationDto> getStudents(@RequestBody UUID sessionId) {
        return this.userService.getStudents(sessionId);
    }
}
