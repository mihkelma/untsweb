package dao;

import model.Customer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@Repository
public class CustomerJpaDao implements CustomerDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Customer> getUserCustomers(String username) {
        return null;
    }

    @Override
    @Transactional
    public void saveCustomer(@Valid Customer customer, String username) {

    }

    @Override
    @Transactional
    public void removeCustomer(Long id, String username) {

    }

    @Override
    public Customer getCustomereById(Long id, String username) {
        return null;
    }
}
