package seniorproject.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seniorproject.business.abstracts.RoleService;
import seniorproject.dataAccess.abstracts.*;
import seniorproject.models.concretes.Role;
import seniorproject.models.concretes.enums.ERole;

import java.util.UUID;

@Service
public class RoleManager implements RoleService{

    private final RoleDao roleDao;
    @Autowired
    public RoleManager(RoleDao roleDao) {
        super();
        this.roleDao = roleDao;
    }

    public UUID generateId(){
        return UUID.randomUUID();
    }

    @Override
    public Role createRole() {
        Role role = new Role();
        role.setId(generateId());
        role.setName(ERole.ROLE_ADMIN);
        System.out.println(role);
        roleDao.save(role);
        return role;
    }
}
