package controller;

import dao.CustomerDao;
import model.Customer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
public class CustomerController {
    //TODO: Allowing customer to login/ logout and see/pay invoice
    @Resource
    CustomerDao customerDao;

    //get all user customers
    @GetMapping("customers")
    @PreAuthorize("hasRole('ROLE_USER') || hasRole('ROLE_ADMIN')")
    public List<Customer> getUserCustomers(Authentication auth) {

        return customerDao.getUserCustomers(auth.getName());
    }

    @PostMapping("customers")
    @PreAuthorize("hasRole('ROLE_USER') || hasRole('ROLE_ADMIN')")
    public void saveCustomer(@RequestBody @Valid Customer customer, Authentication auth) {
        customerDao.saveCustomer(customer, auth.getName());
    }

    @PostMapping("customers/{id}")
    @PreAuthorize("hasRole('ROLE_USER') || hasRole('ROLE_ADMIN')")
    public void removeCustomer(@PathVariable Long id, Authentication auth) {
        customerDao.removeCustomer(id, auth.getName());
    }

    @GetMapping("customers/{id}")
    @PreAuthorize("hasRole('ROLE_USER') || hasRole('ROLE_ADMIN')")
    public Customer getCustomerById(@PathVariable Long id, Authentication auth) {
        return customerDao.getCustomereById(id, auth.getName());
    }
}
