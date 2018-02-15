package dao;

import model.Contract;
import model.Unit;
import model.User;

import java.util.List;

public interface UnitDao {
    //User getAllUserUnits(String username);
    List<Unit> getAllActiveUserUnits(String username);
    List<Unit> getAllInactiveUserUnits(String username);
    List<Contract> getUnitContracts(Long id, String username);
    Unit getUserUnitById(Long id, String username);
    void saveUserUnit(Unit unit, String name);
    void deleteUserUnit(Long id, String name);
}
