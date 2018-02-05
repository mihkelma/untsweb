package controller;

import dao.UnitDao;
import model.Unit;
import model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
public class UnitController {
    @Resource
    private UnitDao unitDao;

    //get all user units
    @GetMapping("units")
    @PreAuthorize("hasRole('ROLE_USER') || hasRole('ROLE_ADMIN')")
    public User getAllUserUnits(Authentication auth) {
        //System.out.println(auth.getName());
        //TODO: is it ok to return user, which has user units as list? Or should I just return units (without user)?
        return unitDao.getAllUserUnits(auth.getName());
    }

    @GetMapping("units/{id}")
    @PreAuthorize("hasRole('ROLE_USER') || hasRole('ROLE_ADMIN')")
    public Unit getUserUnitById(@PathVariable Long id, Authentication auth) {
        return unitDao.getUserUnitById(id, auth.getName());
    }

    //save unit for this user
    @PostMapping("units")
    public void saveUnit(@RequestBody @Valid Unit unit, Authentication auth) {
        unitDao.saveUserUnit(unit, auth.getName());
    }

    //delete unit
    @DeleteMapping("units/{id}")
    public void deleteUserUnit2(@PathVariable Long id) {
        unitDao.deleteUserUnit(id);
    }
}
