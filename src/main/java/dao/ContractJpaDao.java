package dao;

import model.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ContractJpaDao implements ContractDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Contract> getUserContracts(String username) {
        List<Contract> contracts;
        contracts = em.createQuery("SELECT c FROM Contract c LEFT JOIN FETCH c.customer cu " +
                "LEFT JOIN FETCH c.user WHERE lower(c.user.username) = lower(:username) " +
                "AND c.isActive = TRUE", Contract.class)
                .setParameter("username", username)
        .getResultList();
        if (contracts.isEmpty()) return null;
        return contracts;
    }

    @Override
    public Contract getContractById(Long id, String username) {
        try {
            Contract tmp;
            tmp = em.createQuery("SELECT c FROM Contract c LEFT JOIN FETCH c.unit u " +
                    "LEFT JOIN FETCH c.customer cu " +
                    "WHERE c.id = :id AND lower(c.user.username) = lower(:username)", Contract.class)
                    .setParameter("id", id)
                    .setParameter("username", username)
                    .getSingleResult();
            return tmp;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    @Transactional
    public void saveContract(Contract contract, Long id, String username) {

        if (contract.getId() != null) { //existing contract
            Unit unit = em.find(Unit.class, id);
            User user = em.find(User.class, username);
            Customer cust = contract.getCustomer();
            contract.setUnit(unit);
            contract.setCustomer(cust);
            contract.setUser(user);
            //TODO: merging without setting Invoices does not work (needs fix)
            contract.setInvoices(new ArrayList<Invoice>());
            em.merge(contract);
        } else {                        //new contract
            Unit unit = em.find(Unit.class, id);
            User user = em.find(User.class, username);
            Customer cust = contract.getCustomer();
            Customer existing = em.find(Customer.class, cust.getCustomerEmail());
            if (existing == null) {
                cust.setUser(user);
                em.persist(cust);
            }
            contract.setUnit(unit);
            contract.setCustomer(cust);
            contract.setUser(user);
            em.persist(contract);
        }
    }

    @Override
    @Transactional
    public void deleteContract(Long id, String username) {
        Contract contract = em.find(Contract.class, id);
        User user = em.find(User.class, username);
        contract.setIsActive(false);
        contract.setUser(user);
        em.merge(contract);
    }

}
