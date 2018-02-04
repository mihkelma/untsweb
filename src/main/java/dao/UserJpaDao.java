package dao;

import model.User;
import org.springframework.stereotype.Repository;

//import org.apache.logging.log4j.core.Logger;

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
        tmp = em.createQuery("SELECT u FROM User u", User.class).getResultList();
        //tmp = em.createQuery("SELECT u FROM User u LEFT JOIN u.units", User.class).getResultList();
        //System.out.println("JPA users:" +tmp.toString());
        //System.out.println("JPA users:" +tmp2.toString());
        return tmp;
    }

    @Override
    public User findByUsername(String username) {
        return em.createQuery("SELECT p FROM User p WHERE lower(p.username)  = lower(:username)", User.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        if (user.getUsername() == null) {
            //System.out.println(user.toString());
            em.persist(user);
        } else {
            //System.out.println(user.toString());
            em.merge(user);
        }
    }

    @Override
    @Transactional
    public void deleteUser(String username) {
        em.createQuery("delete from User p where lower(p.username) = lower(:username)")
                .setParameter("username", username)
                .executeUpdate();
    }

    @Override
    public User getAllUserUnits2(String username) {
        User tmp;
        tmp = em.createQuery("SELECT u FROM User u LEFT JOIN FETCH u.units2 WHERE lower(u.username) = lower(:username)", User.class)
                .setParameter("username", username)
                .getSingleResult();
        //System.out.println(tmp.toString());
        return tmp;
    }

    @Override
    public void saveUserUnit2(User user) {
        em.persist(user);
    }

    @Override
    public void deleteUserUnit2(Long id) {

    }
}
