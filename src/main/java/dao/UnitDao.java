package dao;

import model.Unit;

import java.util.List;

public interface UnitDao {

    void saveUnit(Unit unit);

    List<Unit> getAllUnits();

    Unit getUnitById(Long id);

    void deleteUnitById(Long id);

    void deleteUnits();

    List<Unit> getUnitByKeyword(String keyword);
}
