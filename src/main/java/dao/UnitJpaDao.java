package dao;

import model.Contract;
import model.Unit;
import model.User;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class UnitJpaDao implements UnitDao {

    @PersistenceContext
    private EntityManager em;

//    @Override
//    public User getAllUserUnits(String username) {
//        User tmp;
//        tmp = em.createQuery("SELECT u FROM User u LEFT JOIN FETCH u.units2 WHERE lower(u.username) = lower(:username)", User.class)
//                .setParameter("username", username)
//                .getSingleResult();
//        //System.out.println(tmp.toString());
//        return tmp;
//    }

    @Override
    public List<Unit> getAllActiveUserUnits(String username) {
        List<Unit> tmp = null;
        try {
            tmp = em.createQuery("SELECT u FROM Unit u LEFT JOIN FETCH u.user " +
                "WHERE lower(u.user.username) = lower(:username) AND u.status = 1", Unit.class)
                .setParameter("username", username)
                .getResultList();
        } catch (NoResultException nre) {}
        if (tmp == null) {
            return null;
        }
        //System.out.println(tmp.toString());
        return tmp;
    }

    @Override
    public List<Unit> getAllInactiveUserUnits(String username) {
        List<Unit> tmp = null;
        try {
            tmp = em.createQuery("SELECT u FROM Unit u LEFT JOIN FETCH u.user " +
                    "WHERE lower(u.user.username) = lower(:username) AND u.status = 0", Unit.class)
                    .setParameter("username", username)
                    .getResultList();
        } catch (NoResultException nre) {}
        if (tmp == null) {
            return null;
        }
        //System.out.println(tmp.toString());
        return tmp;
    }

    @Override
    public Unit getUserUnitById(Long id, String username) {
        //TODO: logic to get one specific unit which belongs to this user
        Unit tmp = null;

        try {
            tmp = (Unit) em.createNativeQuery("SELECT un.* FROM unit un WHERE un.id = :id " +
                    "AND un.username = :username", Unit.class).setParameter("id", id)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {}
        if (tmp == null) {
            System.out.println("getUserUnitById was null");
            return null;
        }
        return tmp;
    }

    @Override
    @Transactional
    public void saveUserUnit(Unit unit, String name) {
        if(unit.getId()!= null) {
            //TODO: check, if this unit belongs to this user
            //TODO: how to update using OO principles
//            em.createNativeQuery("UPDATE UNIT SET name= :name, size= :size, " +
//                    "price = :price, username= :username WHERE id= :id")
//                    .setParameter("name", unit.getName())
//                    .setParameter("size", unit.getSize())
//                    .setParameter("price", unit.getPrice())
//                    .setParameter("username", name).setParameter("id", unit.getId())
//                    .executeUpdate();
            System.out.println("Merging unit");
            User user = em.find(User.class, name);
            unit.setUser(user);
            em.merge(unit);
        } else {
//            User user = em.find(User.class, name);
//            Hibernate.initialize(user.getUnits());
//            user.addUnit(unit);
//            em.persist(user);
            System.out.println("Persisting unit");
            User user = em.find(User.class, name);
            unit.setUser(user);
            em.persist(unit);
        }
    }

    @Override
    @Transactional
    public void deleteUserUnit(Long id, String username) {
        //TODO: check, if this unit belongs to this user and don't delete, but change status to 0 (archive)
        Unit unit = em.find(Unit.class, id);
        User user = em.find(User.class, username);
        unit.setStatus(0);
        unit.setUser(user);
        System.out.println("Removing of user (id:"+ id +") failed");
        em.merge(unit);
    }

    @Override
    public List<Contract> getUnitContracts(Long id, String username) {
        List<Contract> contracts;
        //contracts = em.createQuery("SELECT u.contracts FROM Unit u LEFT JOIN FETCH u.contracts WHERE u.id = :id", Contract.class)
        contracts = em.createQuery("SELECT c FROM Contract c WHERE c.unit.id = :id" +
                " AND c.ownerRef = :username", Contract.class)
                .setParameter("id",id)
                .setParameter("username", username)
                .getResultList();
//        contracts = em.createNativeQuery("SELECT c.* from contract c JOIN unit u ON c.unit_id = u.id WHERE c.unit_id = :id", Contract.class).setParameter("id", id).getResultList();
        if (contracts.isEmpty()) return null;
        return contracts;
    }
}
