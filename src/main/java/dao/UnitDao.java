package dao;

import model.Unit;
import model.User;

import java.util.List;

public interface UnitDao {

    void saveUnit(Unit unit);

    List<Unit> getAllUnits();
    List<Unit> getAllUserUnits(String username);
    User getAllUserUnits2 (String username);

    Unit getUnitById(Long id);

    void deleteUnitById(Long id);

    void deleteUnits();

    List<Unit> getUnitByKeyword(String keyword);
}
