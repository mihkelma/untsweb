package dao;

import model.Unit;
import model.User;
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
        System.out.println(unit.toString());
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
        //tmp2 = em.createNativeQuery("select u.*, un.* from users u LEFT JOIN USERUNIT uu ON u.username = uu.user_username LEFT JOIN UNIT un ON uu.unit_id=un.id",User.class).getResultList();

    }

    @Override
    public List<Unit> getAllUserUnits(String username) {

        List<Unit> tmp;
        //tmp = em.createQuery("select user from User user inner join user.units
        // units where units.id in :dealershipIds", Unit.class);
        return em.createQuery("SELECT u FROM Unit u INNER JOIN u.users WHERE u.users.username = :username", Unit.class)
                .setParameter("username", username)
                .getResultList();
    }

    @Override
    public User getAllUserUnits2 (String username) {
        User tmp;
        tmp = em.createQuery("select u from User u left join fetch u.units where u.username = :username", User.class)
                .setParameter("username", username)
                .getSingleResult();
        return tmp;
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
        em.createQuery("delete from Unit").executeUpdate();
    }

    @Override
    public List<Unit> getUnitByKeyword(String keyword) {
        return em.createQuery("SELECT p FROM Unit p WHERE lower(p.name) LIKE lower(:keyword)", Unit.class)
                .setParameter("keyword", "%"+keyword+"%")
                .getResultList();
    }
}
