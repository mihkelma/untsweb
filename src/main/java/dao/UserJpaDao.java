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
        return em.createQuery("SELECT u FROM User u JOIN u.units", User.class)
                .getResultList();
    }

    @Override
    public List<Unit> getAllUserUnits(User user) {
        return em.createQuery("SELECT u FROM User u ", Unit.class)
                .getResultList();
    }

    @Override
    public User findByUsername(String username) {
        return em.createQuery("SELECT p FROM User p WHERE p.username = :username", User.class)
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
