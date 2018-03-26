package controller;

import dao.InvoiceDao;
import model.Invoice;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
public class InvoiceController {
    //TODO: InvoiceController
    @Resource
    private InvoiceDao invoiceDao;

    //get all user units
    @GetMapping("invoices")
    @PreAuthorize("hasRole('ROLE_USER') || hasRole('ROLE_ADMIN')")
    public List<Invoice> getUserInvoices(Authentication auth) {
        return invoiceDao.getUserInvoices(auth.getName());
    }

    @PostMapping("contracts/{id}/invoices")
    @PreAuthorize("hasRole('ROLE_USER') || hasRole('ROLE_ADMIN')")
    public void saveInvoice(@PathVariable Long id, @RequestBody @Valid Invoice invoice, Authentication auth) {
        invoiceDao.saveInvoice(invoice, id, auth.getName());
    }

    @DeleteMapping("invoices/{id}")
    @PreAuthorize("hasRole('ROLE_USER') || hasRole('ROLE_ADMIN')")
    public void removeInvoice(@PathVariable Long id, Authentication auth) {
        invoiceDao.removeInvoice(id, auth.getName());
    }

    @GetMapping("invoices/{id}")
    @PreAuthorize("hasRole('ROLE_USER') || hasRole('ROLE_ADMIN')")
    public Invoice getInvoiceById(@PathVariable Long id, Authentication auth) {
        return invoiceDao.getInvoiceById(id, auth.getName());
    }
}
