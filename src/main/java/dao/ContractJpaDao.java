package dao;

import model.Contract;
import model.Invoice;
import model.Unit;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class ContractJpaDao implements ContractDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Contract> getUserContracts(String username) {
        List<Contract> contracts;
        contracts = em.createQuery("SELECT c FROM Contract c", Contract.class)
        .getResultList();
        if (contracts.isEmpty()) return null;
        return contracts;
    }

    @Override
    public Contract getContractWithOutInvoices(Long id, String username) {
        try {
            Contract tmp;
            tmp = em.createQuery("Select c FROM Contract c WHERE c.id = :id" +
                    " AND c.ownerRef = :username", Contract.class)
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
    public void saveContract(Contract contract, String username) {
        if (contract.getId() != null) {
            em.find(Contract.class, contract.getId());
            em.merge(contract);
        } else {
            em.persist(contract);
        }
    }

    @Override
    public void deleteContract(Long id, String username) {

    }

    @Override
    public List<Invoice> getContractInvoices(Long id, String username) {
        return null;
    }
}
