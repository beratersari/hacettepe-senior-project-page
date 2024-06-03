package seniorproject.business.concretes;

import org.springframework.stereotype.Service;
import seniorproject.business.abstracts.GroupService;
import seniorproject.core.utilities.results.DataResult;
import seniorproject.core.utilities.results.ErrorDataResult;
import seniorproject.core.utilities.results.SuccessDataResult;
import seniorproject.dataAccess.abstracts.GroupDao;
import seniorproject.dataAccess.abstracts.ProjectTypeDao;
import seniorproject.dataAccess.abstracts.StudentDao;
import seniorproject.models.concretes.*;
import seniorproject.models.concretes.enums.EProjectStatus;
import seniorproject.models.dto.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GroupManager implements GroupService {

    GroupDao groupDao;
    StudentDao studentDao;
    ProjectTypeDao projectTypeDao;

    public GroupManager(GroupDao groupDao, StudentDao studentDao, ProjectTypeDao projectTypeDao) {
        super();
        this.groupDao = groupDao;
        this.studentDao = studentDao;
        this.projectTypeDao = projectTypeDao;
    }

    @Override
    public DataResult<List<GroupDto>> getGroupByStudentId(UUID studentId) {
        List<SeniorProject> projectType = projectTypeDao.findActiveProjectType();
        if (projectType == null) {
            return new DataResult<>(null, false, "Project type not found");
        }
        SeniorProject seniorProject = null;
        if (!projectType.isEmpty()) {
            seniorProject = projectType.get(0);
        }

        List<Group> groups = groupDao.findAllByStudentId(studentId);
        List<GroupDto> groupDtos = new ArrayList<>();


        for(Group group : groups){
            List<ApplicationDto> applicationDtos = new ArrayList<>();
            for (Application application : group.getApplications()){
                if (seniorProject!=null && application.getProject().getProjectType().getId() == seniorProject.getId()) {
                    applicationDtos.add(application.toApplicationDto());
                }
            }
            GroupDto groupDto = group.toGroupDto();
            groupDto.setApplications(applicationDtos);
            groupDtos.add(groupDto);
        }
        return new SuccessDataResult<>(groupDtos);
    }

    @Override
    public DataResult<GroupDto> createStudentGroup(StudentGroupCreateRequestDto requestDto) {
        Group group = new Group();
        groupDao.save(group);

        List<Student> students = new ArrayList<>();
        group.setName(requestDto.getGroupName());

        if (requestDto.getGroupMembers() != null) {
            for (StudentInformationDto memberDto : requestDto.getGroupMembers()) {
                studentDao.findById(memberDto.getId()).ifPresent(students::add);
            }
        }

        studentDao.findById(requestDto.getSessionId()).ifPresent(sessionStudent -> {
            if (!students.contains(sessionStudent)) {
                students.add(sessionStudent);
            }
        });

        for (Student student : students) {
            List<Group> groups = student.getGroups() != null ? student.getGroups() : new ArrayList<>();
            groups.add(group);
            student.setGroups(groups);
            studentDao.save(student);
        }

        group.setStudents(students);
        groupDao.save(group);


        return new SuccessDataResult<>(group.toGroupDto());
    }


    @Override
    public DataResult<GroupDto> deleteStudentGroup(UUID groupId) {
        Group group = groupDao.findById(groupId).orElse(null);
        if (group == null) {
            return new ErrorDataResult<>(null, "Group not found");
        }

        if(group.getProjects() != null){
            for(Project project : group.getProjects()){
                if(project.getEProjectStatus().equals(EProjectStatus.ARCHIVED) || project.getEProjectStatus().equals(EProjectStatus.WORKING)){
                    return new ErrorDataResult<>(null, "First you need to delete all projects in the group");
                }
            }
        }

        if(group.getStudents() != null){
            for(Student student : group.getStudents()){
                student.getGroups().remove(group);
                studentDao.save(student);
            }
        }
        groupDao.deleteById(groupId);

        return new SuccessDataResult<>(null, "Group deleted successfully");
    }

    @Override
    public DataResult<GroupDto> updateStudentGroup(UpdateGroupDto studentGroupUpdateRequestDto) {
        Optional<Group> group = groupDao.findById(studentGroupUpdateRequestDto.getGroupId());
        if (group.isPresent()){
            List<Student> newStudents = new ArrayList<>();

            List<Student> previousStudents = group.get().getStudents();

            if (studentGroupUpdateRequestDto.getGroupMembers() != null) {
                for (StudentInformationDto studentInformationDto : studentGroupUpdateRequestDto.getGroupMembers()) {
                    studentDao.findById(studentInformationDto.getId()).ifPresent(newStudents::add);
                }
            }

            Student student = studentDao.findById(studentGroupUpdateRequestDto.getSessionId()).orElse(null);
            if (!newStudents.contains(student)) {
                newStudents.add(student);
            }

            for(Student student1 : previousStudents){
                student1.getGroups().remove(group.get());
                studentDao.save(student1);
            }

            for(Student student1 : newStudents){
                student1.getGroups().add(group.get());
                studentDao.save(student1);
            }

            group.get().setStudents(newStudents);
            group.get().setName(studentGroupUpdateRequestDto.getGroupName());


            groupDao.save(group.get());
            return new SuccessDataResult<>(group.get().toGroupDto());

        }
        return new SuccessDataResult<>(null,"Data couldn't update.");
    }

}
