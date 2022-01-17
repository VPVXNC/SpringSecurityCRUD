package myapp.dao;


import org.springframework.stereotype.Repository;
import myapp.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDAOImpl implements RoleDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveRole(Role role) {
        entityManager.persist(role);
    }

    @Override
    public Role getRoleByName(String name) {
        return entityManager.createQuery("from Role where name = :name", Role.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    @Override
    public Role getRoleById(Long id) {
        return entityManager.find(Role.class, id);
    }


    @Override
    public List<Role> getAllRoles() {
        return entityManager.createQuery("from Role", Role.class).getResultList();
    }
}
