package dao;

import model.Unit;
import model.User;

public interface UnitDao {
    User getAllUserUnits(String username);
    Unit getUserUnitById(Long id, String username);
    Unit getUserUnitByIdWithOutInvoices(Long id, String username);
    void saveUserUnit(Unit unit, String name);
    void deleteUserUnit(Long id);
}
