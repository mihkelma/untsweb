package dao;

import model.User;

import java.util.List;

public interface UserDao {
    List<User> getAllUsers();

    User findByUsername(String username);

    void saveUser(User user);

    void deleteUser(String username);

    // Unit2 example
    User getAllUserUnits2(String username);

    void saveUserUnit2(User user);

    void deleteUserUnit2(Long id);
}
