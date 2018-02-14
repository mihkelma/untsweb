package controller;

import dao.ContractDao;
import dao.UnitDao;
import model.Contract;
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
    private ContractDao contractDao;

    //get all user units
    @GetMapping("units")
    @PreAuthorize("hasRole('ROLE_USER') || hasRole('ROLE_ADMIN')")
    public User getAllUserUnits(Authentication auth) {
        //System.out.println(auth.getName());
        //TODO: is it ok to return user, which has user units as list? Or should I just return units (without user)?
        return unitDao.getAllUserUnits(auth.getName());
    }

    @GetMapping("units2")
    @PreAuthorize("hasRole('ROLE_USER') || hasRole('ROLE_ADMIN')")
    public List<Unit> getAllUserUnits2(Authentication auth) {
        //System.out.println(auth.getName());
        //TODO: is it ok to return user, which has user units as list? Or should I just return units (without user)?
        return unitDao.getAllUserUnits2(auth.getName());
    }

    @GetMapping("units/{id}")
    @PreAuthorize("hasRole('ROLE_USER') || hasRole('ROLE_ADMIN')")
    public Unit getUserUnitById(@PathVariable Long id, Authentication auth) {
        return unitDao.getUserUnitById(id, auth.getName());
    }

    //save unit for this user
    @PostMapping("units")
    @PreAuthorize("hasRole('ROLE_USER') || hasRole('ROLE_ADMIN')")
    public void saveUnit(@RequestBody @Valid Unit unit, Authentication auth) {
        unitDao.saveUserUnit(unit, auth.getName());
    }

    //delete unit
    @DeleteMapping("units/{id}")
    @PreAuthorize("hasRole('ROLE_USER') || hasRole('ROLE_ADMIN')")
    public void deleteUserUnit2(@PathVariable Long id, Authentication auth) {
        unitDao.deleteUserUnit(id, auth.getName());
    }

    @GetMapping("units/{id}/contracts")
    @PreAuthorize("hasRole('ROLE_USER') || hasRole('ROLE_ADMIN')")
    public List<Contract> getUnitContracts(@PathVariable Long id, Authentication auth) {
        //System.out.println(auth.getName());
        return unitDao.getUnitContracts(id, auth.getName());
    }
}
//http://localhost:8080/api/units/1/contracts