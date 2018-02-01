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

    @GetMapping("users/units")
    @PreAuthorize("hasRole('ROLE_USER') || hasRole('ROLE_ADMIN')")
    public List<Unit> getAllUserUnits(Authentication auth) {
        System.out.println(auth.getName());
        return userDao.getAllUserUnits(auth.getName());
    }

    @PostMapping("users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void saveUser(@RequestBody @Valid User user) {
        User newUser = new User();
        System.out.println(user.toString());
        newUser.setUsername(user.getUsername());
        newUser.setName(user.getName());
        newUser.setEnabled(user.getEnabled());
        newUser.setPassword(user.getPassword());
        userDao.saveUser(newUser);
    }
}
