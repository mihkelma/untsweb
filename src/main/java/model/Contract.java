package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "Contract")
@Table(name = "contract")
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class Contract {

    @Id
    @SequenceGenerator(name = "my_seq", sequenceName = "seq2", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_seq")
    private Long id;
    private Date created;
    private Boolean isActive;
    private Date closed;
    private Date dueDate;
    private Integer invoiceDate;
    private String ownerName;
    private String ownerAddress;
    private String ownerPhone;
    private String ownerEmail;
    private String ownerIBAN;
    private String ownerBank;
    private String ownerNotes;
    private Boolean isVATRequired;
    private String ownerSalesName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id")
    @JsonIgnore
    private Unit unit;

    //TODO: add, merge, remove Invoices
    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Invoice> invoices = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customeremail")
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "username")
    @JsonIgnore
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contract)) return false;
        return id != null && id.equals(((Contract) o).id);
    }
    @Override
    public int hashCode() {
        return 32;
    }

}
