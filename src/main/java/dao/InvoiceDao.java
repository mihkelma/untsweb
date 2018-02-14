package dao;

import model.Invoice;

import javax.validation.Valid;
import java.util.List;

public interface InvoiceDao {

    List<Invoice> getUserInvoices(String username);

    void saveInvoice(@Valid Invoice invoice, String username);

    void removeInvoice(Long id, String username);

    Invoice getInvoiceById(Long id, String username);
}
