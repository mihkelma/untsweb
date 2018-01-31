package controller;


import dao.UnitDao;
import model.Unit;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
public class UnitController {
    @Resource
    private UnitDao unitDao;

    // api/units on liiga palju, kuna see juba AppInitaliseris kirjas - api/*
    @GetMapping("units")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public List<Unit> getAllUnits() { return unitDao.getAllUnits(); }

    @GetMapping("units/{id}")
    public Unit getUnitById(@PathVariable Long id) { return unitDao.getUnitById(id); }

    @GetMapping("units/search")
    public List<Unit> getUnitByKey(@RequestParam("key") String keyword) {
        System.out.println(keyword);
        return unitDao.getUnitByKeyword(keyword); }

    @PostMapping("units")
    public void saveUnit(@RequestBody @Valid Unit unit) {
        unitDao.saveUnit(unit);
    }

    @DeleteMapping("units/{id}")
    public void deleteUnit(@PathVariable Long id) {
        unitDao.deleteUnitById(id);
    }

    @DeleteMapping("units")
    public void deleteAllUnits() {
        unitDao.deleteUnits();
    }

}
