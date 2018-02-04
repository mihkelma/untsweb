package controller;


import dao.UnitDao;
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
public class UnitController {
    @Resource
    private UnitDao unitDao;

    @Resource
    private UserDao userDao;


    // api/units on liiga palju, kuna see juba AppInitaliseris kirjas - api/*
    //get all units
    @GetMapping("allunits")
    @PreAuthorize("hasRole('ROLE_USER') || hasRole('ROLE_ADMIN')")
    public List<Unit> getAllUnits() {
        //System.out.println(auth.getName());
        return unitDao.getAllUnits();
    }

    @GetMapping("units1")
    @PreAuthorize("hasRole('ROLE_USER') || hasRole('ROLE_ADMIN')")
    public List<Unit> getAllUserUnits(Authentication auth) {
        //System.out.println(auth.getName());
        return unitDao.getAllUserUnits(auth.getName());
    }

    @GetMapping("units")
    @PreAuthorize("hasRole('ROLE_USER') || hasRole('ROLE_ADMIN')")
    public User getAllUserUnits2(Authentication auth) {
        //System.out.println(auth.getName());
        return unitDao.getAllUserUnits2(auth.getName());
    }

    //get unit by id
    @GetMapping("units/{id}")
    public Unit getUnitById(@PathVariable Long id) { return unitDao.getUnitById(id); }

    //get unit by name
    @GetMapping("units/search")
    public List<Unit> getUnitByKey(@RequestParam("key") String keyword) {
        System.out.println(keyword);
        return unitDao.getUnitByKeyword(keyword); }

    //save or merge unit
    @PostMapping("units")
    public void saveUnit(@RequestBody @Valid Unit unit, Authentication auth) {
        //User user = userDao.findByUsername(auth.getName());
        //user.getUnits().add(unit);
        //userDao.saveUser(user);
        unitDao.saveUnit(unit);
    }

    //delete unit
    @DeleteMapping("units/{id}")
    public void deleteUnit(@PathVariable Long id) {
        unitDao.deleteUnitById(id);
    }

    @DeleteMapping("units")
    public void deleteAllUnits() {
        unitDao.deleteUnits();
    }

}
