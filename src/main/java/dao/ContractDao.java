package dao;

import model.Contract;
import model.Customer;
import model.Invoice;
import model.Unit;

import java.util.List;

public interface ContractDao {
    List<Contract> getUserContracts(String username);
    Contract getContractById(Long id, String username);
    void saveContract(Contract contract, Long id, String username);
    void deleteContract(Long id, String username);
}
