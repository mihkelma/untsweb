package dao;

import model.Contract;
import model.Invoice;
import model.InvoiceRow;
import model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Repository
public class InvoiceJpaDao implements InvoiceDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Invoice> getUserInvoices(String username) {
        List<Invoice> invoices;
        invoices = em.createQuery("SELECT i FROM Invoice i " +
                "LEFT JOIN FETCH i.user WHERE lower(i.user.username) = lower(:username) ", Invoice.class)
                .setParameter("username", username)
                .getResultList();
        if (invoices.isEmpty()) return null;
        return invoices;
    }

    @Override
    public List<Invoice> getContractInvoices(Long id, String username) {
        List<Invoice> invoices;
        invoices = em.createQuery("SELECT i FROM Invoice i " +
                "LEFT JOIN FETCH i.user WHERE lower(i.user.username) = lower(:username) " +
                "AND i.contract.id = :id", Invoice.class)
                .setParameter("username", username)
                .setParameter("id", id)
                .getResultList();
        if (invoices.isEmpty()) return null;
        return invoices;
    }

    @Override
    @Transactional
    public void saveInvoice(@Valid Invoice invoice, Long id, String username) {

        if (invoice.getId() != null) { //existing contract
            Contract contract = em.find(Contract.class, id);
            User user = em.find(User.class, username);
            invoice.setContract(contract);
            invoice.setUser(user);
            //TODO: merging without setting InvoiceRows does not work (needs fix)
            invoice.setInvoiceRows(new ArrayList<InvoiceRow>());
            em.merge(invoice);
        } else {                        //new contract
            Contract contract = em.find(Contract.class, id);
            User user = em.find(User.class, username);
            invoice.setContract(contract);
            invoice.setUser(user);
            invoice.setUser(user);
            em.persist(invoice);
        }

    }

    @Override
    @Transactional
    public void removeInvoice(Long id, String username) {
        Invoice invoice = em.find(Invoice.class, id);
        User user = em.find(User.class, username);
        invoice.setStatus(5);
        invoice.setUser(user);
        em.merge(invoice);
    }

    @Override
    public Invoice getInvoiceById(Long id, String username) {
        try {
            Invoice tmp;
            tmp = em.createQuery("SELECT i FROM Invoice i " +
                    "LEFT JOIN FETCH i.user u LEFT JOIN FETCH i.invoiceRows ir " +
                    "WHERE i.id = :id AND lower(i.user.username) = lower(:username)", Invoice.class)
                    .setParameter("id", id)
                    .setParameter("username", username)
                    .getSingleResult();
            return tmp;
        } catch (NoResultException e) {
            return null;
        }
    }
}
