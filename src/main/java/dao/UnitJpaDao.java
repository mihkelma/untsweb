package dao;

import model.Unit;
import model.User;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class UnitJpaDao implements UnitDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public User getAllUserUnits(String username) {
        User tmp;
        tmp = em.createQuery("SELECT u FROM User u LEFT JOIN FETCH u.units2 WHERE lower(u.username) = lower(:username)", User.class)
                .setParameter("username", username)
                .getSingleResult();
        //System.out.println(tmp.toString());
        return tmp;
    }

    @Override
    public Unit getUserUnitById(Long id, String username) {
        //TODO: logic to get one specific unit which belongs to this user
        Unit tmp = (Unit) em.createNativeQuery("SELECT un.* FROM unit un WHERE un.id = :id AND un.username = :username", Unit.class).setParameter("id", id)
                .setParameter("username", username)
                .getSingleResult();
        return tmp;
    }

    @Override
    public Unit getUserUnitByIdWithOutInvoices(Long id, String username) {
        //TODO: logic to get one specific unit which belongs to this user, without invoices
        return null;
    }

    @Override
    @Transactional
    public void saveUserUnit(Unit unit, String name) {
        if(unit.getId()!= null) {
            //TODO: check, if this unit belongs to this user
            //TODO: how to update using OO principles
            em.createNativeQuery("UPDATE UNIT SET name= :name, size= :size, " +
                    "price = :price, username= :username WHERE id= :id")
                    .setParameter("name", unit.getName())
                    .setParameter("size", unit.getSize())
                    .setParameter("price", unit.getPrice())
                    .setParameter("username", name).setParameter("id", unit.getId())
                    .executeUpdate();
        } else {
            User user = em.find(User.class, name);
            Hibernate.initialize(user.getUnits2());
            user.addUnit2(unit);
            em.persist(user);
        }
    }

    @Override
    @Transactional
    public void deleteUserUnit(Long id) {
        //TODO: check, if this unit belongs to this user and don't delete, but change status to 0 (archive)
        Unit unit = em.find(Unit.class, id);
        em.remove(unit);
    }
}
