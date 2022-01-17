package myapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import myapp.dao.RoleDAOImpl;
import myapp.model.Role;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class RoleServiceImpl implements RoleService{
    RoleDAOImpl roleDAO;

    @Autowired
    public void setRoleDAO(RoleDAOImpl roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Override
    public void saveRole(Role role) {
        roleDAO.saveRole(role);
    }

    @Override
    @Transactional(readOnly = true)
    public Role getRoleById(Long id) {
        return roleDAO.getRoleById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Role getRoleByName(String name) {
        return roleDAO.getRoleByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> getAllRoles() {
        return roleDAO.getAllRoles();
    }

    @Override
    public Set<Role> getSetOfRoles(String[] roles) {
        Set<Role> rolesSet = new HashSet<>();

        for (String role : roles) {
            rolesSet.add(roleDAO.getRoleByName(role));
        }
        return rolesSet;
    }
}
