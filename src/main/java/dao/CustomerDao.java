package dao;

import model.Customer;

import javax.validation.Valid;
import java.util.List;

public interface CustomerDao {
    public List<Customer> getUserCustomers(String username);
    public void saveCustomer(@Valid Customer customer, String username);
    public void removeCustomer(Long id, String username);
    public Customer getCustomereById(Long id, String username);
}
