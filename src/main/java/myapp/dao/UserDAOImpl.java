package myapp.dao;

import myapp.model.Role;
import myapp.model.User;
import myapp.service.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class UserDAOImpl implements UserDAO {
    @PersistenceContext
    private EntityManager entityManager;

    private RoleServiceImpl roleService;

    @Autowired
    public void setRoleService(RoleServiceImpl roleService) {
        this.roleService = roleService;
    }

    @Override
    public void saveUser(User user) {
        List<Role> currentRoles = roleService.getAllRoles();
        Set<Role> userRoles = user.getRoles();
        Set<Role> rolesForNewUser = new HashSet<>();

        for (Role currentRole : currentRoles) {
            for (Role userRole : userRoles) {
                if (userRole.getName().equals(currentRole.getName())) {
                    rolesForNewUser.add(roleService.getRoleById(currentRole.getId()));
                }
            }
        }

        user.setRoles(rolesForNewUser);
        entityManager.persist(user);
    }

    @Override
    public void updateUser(Long id, User updatedUser) {
        List<Role> currentRoles = roleService.getAllRoles();
        Set<Role> userRoles = updatedUser.getRoles();
        Set<Role> rolesForNewUser = new HashSet<>();

        for (Role currentRole : currentRoles) {
            for (Role userRole : userRoles) {
                if (userRole.getName().equals(currentRole.getName())) {
                    rolesForNewUser.add(roleService.getRoleById(currentRole.getId()));
                }
            }
        }

        User userToBeUpdated = getUserById(id);

        userToBeUpdated.setName(updatedUser.getName());
        userToBeUpdated.setLastName(updatedUser.getLastName());
        userToBeUpdated.setAge(updatedUser.getAge());
        userToBeUpdated.setEmail(updatedUser.getEmail());
        userToBeUpdated.setRoles(rolesForNewUser);
        userToBeUpdated.setPassword(updatedUser.getPassword());

        entityManager.merge(userToBeUpdated);
    }

    @Override
    public User getUserById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User getByName(String name) {
        return entityManager.createQuery("from User where name = :name", User.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    @Override
    public void removeUserById(Long id) {
        entityManager.remove(getUserById(id));
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }
}
