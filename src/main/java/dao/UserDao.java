package dao;

import model.Unit;
import model.User;

import java.util.List;

public interface UserDao {
    List<User> getAllUsers();
    //public List<Unit> getAllUserUnits();
    public List<Unit> getAllUserUnits(User user);

    User findByUsername(String username);

    void saveUser(User user);
}
