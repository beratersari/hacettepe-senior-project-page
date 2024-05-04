package seniorproject.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import seniorproject.business.abstracts.GroupService;
import seniorproject.core.utilities.results.DataResult;
import seniorproject.models.dto.GroupDto;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/groups")
public class GroupsController {

    GroupService groupService;

    @Autowired
    public GroupsController(GroupService groupService) {
        super();
        this.groupService = groupService;
    }

    @PostMapping("/getGroupByStudentId")
    public DataResult<List<GroupDto>> getGroupByStudentId(Long studentId) {
        return this.groupService.getGroupByStudentId(studentId);
    }
}
