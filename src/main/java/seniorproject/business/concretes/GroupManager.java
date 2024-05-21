package seniorproject.business.concretes;

import org.springframework.stereotype.Service;
import seniorproject.business.abstracts.GroupService;
import seniorproject.core.utilities.results.DataResult;
import seniorproject.core.utilities.results.ErrorDataResult;
import seniorproject.core.utilities.results.SuccessDataResult;
import seniorproject.dataAccess.abstracts.GroupDao;
import seniorproject.dataAccess.abstracts.StudentDao;
import seniorproject.models.concretes.Group;
import seniorproject.models.concretes.Project;
import seniorproject.models.concretes.Student;
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

    public GroupManager(GroupDao groupDao, StudentDao studentDao) {
        super();
        this.groupDao = groupDao;
        this.studentDao = studentDao;
    }

    @Override
    public DataResult<List<GroupDto>> getGroupByStudentId(UUID studentId) {
        List<Group> groups = groupDao.findAllByStudentId(studentId);
        System.out.println(groups);
        List<GroupDto> groupDtos = new ArrayList<>();

        for(Group group : groups){
            GroupDto groupDto = group.toGroupDto();
            groupDtos.add(groupDto);
        }
        return new SuccessDataResult<>(groupDtos);
    }

    @Override
    public DataResult<GroupDto> createStudentGroup(StudentGroupCreateRequestDto studentGroupCreateRequestDto) {
        Group group = new Group();
        List<Student> students = new ArrayList<>();

        if (studentGroupCreateRequestDto.getStudents() != null) {
            for (StudentInformationDto studentInformationDto : studentGroupCreateRequestDto.getStudents()) {
                studentDao.findById(studentInformationDto.getId()).ifPresent(students::add);
            }
        }

        Student student = studentDao.findById(studentGroupCreateRequestDto.getSessionId()).orElse(null);
        if (!students.contains(student)) {
            students.add(student);
        }

        group.setStudents(students);
        group.setName(studentGroupCreateRequestDto.getGroupName());

        for(Student student1 : students){
            student1.getGroups().add(group);
            studentDao.save(student1);
        }

        groupDao.save(group);


        return new SuccessDataResult<>(group.toGroupDto());
    }

    @Override
    public DataResult<GroupDto> deleteStudentGroup(UUID groupId) {
        System.out.println("Group id: " + groupId);
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
                    System.out.println("Student id: " + studentInformationDto.getId());
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
