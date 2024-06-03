package seniorproject.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import seniorproject.business.abstracts.GroupService;
import seniorproject.core.utilities.results.DataResult;
import seniorproject.models.dto.GroupApplicationDto;
import seniorproject.models.dto.GroupDto;
import seniorproject.models.dto.StudentGroupCreateRequestDto;
import seniorproject.models.dto.UpdateGroupDto;

import java.util.List;
import java.util.UUID;

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

    @PostMapping("/getGroupsByStudentId")
    @PreAuthorize("hasAnyRole('ROLE_STUDENT', 'ROLE_PROFESSOR')")
    public DataResult<List<GroupDto>> getGroupByStudentId(@RequestBody UUID studentId) {
        return this.groupService.getGroupByStudentId(studentId);
    }

    @PostMapping("/createStudentGroup")
    @PreAuthorize("hasAnyRole('ROLE_STUDENT', 'ROLE_PROFESSOR')")
    public DataResult<GroupDto> createStudentGroup(@RequestBody StudentGroupCreateRequestDto studentGroupCreateRequestDto) {
        return this.groupService.createStudentGroup(studentGroupCreateRequestDto);
    }

    @PostMapping("/deleteStudentGroup")
    @PreAuthorize("hasAnyRole('ROLE_STUDENT', 'ROLE_PROFESSOR')")
    public DataResult<GroupDto> deleteStudentGroup(@RequestBody UUID groupId) {
        return this.groupService.deleteStudentGroup(groupId);
    }

    @PostMapping("/updateStudentGroup")
    @PreAuthorize("hasAnyRole('ROLE_STUDENT', 'ROLE_PROFESSOR')")
    public DataResult<GroupDto> updateStudentGroup(@RequestBody UpdateGroupDto groupDto) {
        return this.groupService.updateStudentGroup(groupDto);
    }

}
