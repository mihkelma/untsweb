package dao;

import model.Unit;
import model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class UserJpaDao implements UserDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<User> getAllUsers() {

        List<User> tmp;
        tmp = em.createQuery("SELECT u FROM User u JOIN u.units", User.class)
                .getResultList();
        System.out.println(tmp.toString());
        return tmp;
    }

    @Override
    public List<Unit> getAllUserUnits(String username) {
        return em.createQuery("SELECT u.units FROM User u JOIN u.units WHERE u.users.username = :username", Unit.class)
                .setParameter("username", username)
                .getResultList();
    }

    @Override
    public User findByUsername(String username) {
        return em.createQuery("SELECT p FROM User p JOIN p.units WHERE p.username = :username", User.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        if (user.getUsername() == null) {
            em.persist(user);
        } else {
            em.merge(user);
        }
    }
}
