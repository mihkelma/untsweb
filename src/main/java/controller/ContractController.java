package controller;

import dao.ContractDao;
import model.Contract;
import model.Customer;
import model.Invoice;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
public class ContractController {
    //TODO: ContractController
    @Resource
    private ContractDao contractDao;

    @GetMapping("contracts")
    @PreAuthorize("hasRole('ROLE_USER') || hasRole('ROLE_ADMIN')")
    public List<Contract> getUserContracts(Authentication auth) {
        //System.out.println(auth.getName());
        return contractDao.getUserContracts(auth.getName());
    }

    @PostMapping("units/{id}/contracts")
    @PreAuthorize("hasRole('ROLE_USER') || hasRole('ROLE_ADMIN')")
    public void saveContract(@PathVariable Long id, @RequestBody @Valid Contract contract, Authentication auth) {
        //System.out.println("Contr" +contract.getOwnerName() + ", "+contract.getCustomer());
        contractDao.saveContract(contract, id, auth.getName());
    }

    @DeleteMapping("contracts/{id}")
    public void deleteContract(@PathVariable Long id, Authentication auth) {
        contractDao.deleteContract(id, auth.getName());
    }

    @GetMapping("contracts/{id}")
    @PreAuthorize("hasRole('ROLE_USER') || hasRole('ROLE_ADMIN')")
    public Contract getContractById(@PathVariable Long id, Authentication auth) {
        return contractDao.getContractById(id, auth.getName());
    }

    @GetMapping("contracts/{id}/invoices")
    @PreAuthorize("hasRole('ROLE_USER') || hasRole('ROLE_ADMIN')")
    public List<Invoice> getContractInvoices(@PathVariable Long id, Authentication auth) {
        return contractDao.getContractInvoices(id, auth.getName());
    }
}
