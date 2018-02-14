package dao;

import model.Invoice;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@Repository
public class InvoiceJpaDao implements InvoiceDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Invoice> getUserInvoices(String username) {
        return null;
    }

    @Override
    @Transactional
    public void saveInvoice(@Valid Invoice invoice, String username) {

    }

    @Override
    @Transactional
    public void removeInvoice(Long id, String username) {

    }

    @Override
    public Invoice getInvoiceById(Long id, String username) {
        return null;
    }
}
