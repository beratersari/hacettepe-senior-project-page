package seniorproject.business.concretes;

import org.springframework.stereotype.Service;
import seniorproject.business.abstracts.GroupService;
import seniorproject.core.utilities.results.DataResult;
import seniorproject.core.utilities.results.SuccessDataResult;
import seniorproject.dataAccess.abstracts.GroupDao;
import seniorproject.models.concretes.Group;
import seniorproject.models.dto.GroupDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupManager implements GroupService {

    GroupDao groupDao;

    public GroupManager(GroupDao groupDao) {
        super();
        this.groupDao = groupDao;
    }

    @Override
    public DataResult<List<GroupDto>> getGroupByStudentId(Long studentId) {
        List<Group> groups = groupDao.findAllByStudentId(studentId);
        List<GroupDto> groupDtos = new ArrayList<>();

        for(Group group : groups){
            GroupDto groupDto = group.toGroupDto();
            groupDtos.add(groupDto);
        }
        return new SuccessDataResult<>(groupDtos);
    }

}
