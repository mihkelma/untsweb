package controller;

import dao.UserDao;
import model.Unit2;
import model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
public class Unit2Controller {
    @Resource
    private UserDao userDao;

    @GetMapping("units2")
    @PreAuthorize("hasRole('ROLE_USER') || hasRole('ROLE_ADMIN')")
    public User getAllUnits2(Authentication auth) {
        //System.out.println(auth.getName());
        return userDao.getAllUserUnits2(auth.getName());
    }

    @PostMapping("units2")
    public void saveUnit(@RequestBody @Valid Unit2 unit, Authentication auth) {
        User user = userDao.findByUsername(auth.getName());
        user.addUnit2(unit);
        //userDao.saveUser(user);
        userDao.saveUserUnit2(user);
    }

    //delete unit
    @DeleteMapping("units2/{id}")
    public void deleteUserUnit2(@PathVariable Long id) {
        userDao.deleteUserUnit2(id);
    }
}
