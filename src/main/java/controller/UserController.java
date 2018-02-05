package controller;

import dao.UserDao;
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
        //System.out.println(username);
        //System.out.println(auth.getAuthorities());
        return userDao.findByUsername(username);
    }

    @GetMapping("users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @PostMapping("users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void saveUser(@RequestBody @Valid User user) {
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setPhone(user.getPhone());
        newUser.setEmail(user.getEmail());
        newUser.setEnabled(user.getEnabled());
        newUser.setPassword(user.getPassword());
        userDao.saveUser(newUser);
    }

    //@DeleteMapping("users/{username}")
    @RequestMapping(value = "users/{username}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteUser(@PathVariable String username) {
        userDao.deleteUser(username);
    }
}
