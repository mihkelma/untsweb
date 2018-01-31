package controller;

import dao.UserDao;
import model.Unit;
import model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {
    @Resource
    private UserDao userDao;

    @GetMapping("users/{username}")
    @PreAuthorize("#username == authentication.name || hasRole('ROLE_ADMIN')")
    public User getUserByName(@PathVariable String username, Authentication auth) {
        System.out.println(username);
        System.out.println(auth.getAuthorities());
        return userDao.findByUsername(username);
    }

    @GetMapping("users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

//    @GetMapping("users/units")
//    @PreAuthorize("#username == authentication.name")
//    public List<Unit> getAllUserUnits(Authentication auth) {
//
//        return userDao.getAllUserUnits();
//    }

    @PostMapping("users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void saveUser(@RequestBody @Valid User user) {
        User newUser = new User();
        System.out.println(user.toString());
        newUser.setUsername(user.getUsername());
        newUser.setUsername(user.getName());
        newUser.setEnabled(user.getEnabled());
        newUser.setPassword(user.getPassword());
        userDao.saveUser(newUser);
    }
}
