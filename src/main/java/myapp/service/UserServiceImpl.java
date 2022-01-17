package myapp.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import myapp.dao.UserDAOImpl;
import myapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
    UserDAOImpl userDAO;

    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public void setUserDAO(UserDAOImpl userDAO) {
        this.userDAO = userDAO;
    }

    @Autowired
    public void setPasswordEncoder(@Lazy BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDAO.saveUser(user);
    }

    @Override
    public void updateUser(Long id, User user) {
        if (!user.getPassword().equals(getUserById(user.getId()).getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userDAO.updateUser(id, user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userDAO.getUserById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User getByName(String name) {
        return userDAO.getByName(name);
    }

    @Override
    public void removeUserById(Long id) {
        userDAO.removeUserById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getByName(username);
    }
}
