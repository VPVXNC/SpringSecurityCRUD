package myapp.dao;

import myapp.model.User;

import java.util.List;

public interface UserDAO {
    void saveUser(User user);
    void updateUser(Long id, User user);
    User getUserById(Long id);
    User getByName(String name);
    void removeUserById(Long id);
    List<User> getAllUsers();
}
