package dao;

import model.User;

import java.util.List;

public interface UserDao {
    List<User> getAllUsers();

    User findByUsername(String username);

    void saveUser(User user);

    void deleteUser(String username);

}
