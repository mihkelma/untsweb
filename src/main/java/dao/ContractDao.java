package dao;

import model.Contract;
import model.Invoice;

import java.util.List;

public interface ContractDao {
    List<Contract> getUserContracts(String username);
    Contract getContractWithOutInvoices(Long id, String username);
    void saveContract(Contract contract, String username);
    void deleteContract(Long id, String username);
    List<Invoice> getContractInvoices(Long id, String username);
}
