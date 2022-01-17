package myapp.dao;

import myapp.model.Role;

import java.util.List;

public interface RoleDAO {
    List<Role> getAllRoles();
    void saveRole(Role role);
    Role getRoleById(Long id);
    Role getRoleByName(String name);
}
