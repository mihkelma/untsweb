package dao;

import model.Unit;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class UnitJpaDao implements UnitDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void saveUnit(Unit unit) {
        if (unit.getId() == null) {
            em.persist(unit);
        } else {
            em.merge(unit);
        }
    }

    @Override
    public List<Unit> getAllUnits() {
        return em.createQuery("SELECT p FROM Unit p", Unit.class)
                .getResultList();
    }

    @Override
    public Unit getUnitById(Long id) {
        return em.createQuery("SELECT p FROM Unit p WHERE p.id = :id", Unit.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    @Transactional
    public void deleteUnitById(Long id) {
        em.createQuery("delete from Unit p where p.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    @Transactional
    public void deleteUnits() {
        em.createQuery("delete from Unit")
                .executeUpdate();
    }

    @Override
    public List<Unit> getUnitByKeyword(String keyword) {
        return em.createQuery("SELECT p FROM Unit p WHERE lower(p.name) LIKE lower(:keyword)", Unit.class)
                .setParameter("keyword", "%"+keyword+"%")
                .getResultList();
    }
}
