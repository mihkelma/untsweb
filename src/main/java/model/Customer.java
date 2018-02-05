package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "Customer")
@Table(name = "customer")
public class Customer {

    @Id
    private String customerEmail;
    private String customerName;
    private String customerAddress;
    private String customerPhone;
    private String customerReference;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contract> contracts = new ArrayList<>();
}