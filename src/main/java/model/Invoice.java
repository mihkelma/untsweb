package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Entity(name = "Invoice")
@Table(name = "invoice")
public class Invoice {

    @Id
    @SequenceGenerator(name = "my_seq", sequenceName = "seq3", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_seq")
    private Long id;
    private Date dateCreated;
    private Date dateDue;
    private Integer status; //draft - 0, ready -1, queue -2, sent -3, paid -4, deleted - 5
    private String ownerName;
    private String ownerAddress;
    private String ownerPhone;
    private String ownerEmail;
    private String ownerIBAN;
    private String ownerBank;
    private String ownerNotes;
    private Boolean isVATRequired;
    private String ownerSalesName;
    private String customerEmail;
    private String customerName;
    private String customerAddress;
    private String customerPhone;
    private String customerReference;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id")
    @JsonIgnore
    private Contract contract;

    //TODO: add, merge, remove invoiceRow
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<InvoiceRow> invoiceRows = new ArrayList<InvoiceRow>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "username")
    @JsonIgnore
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Invoice)) return false;
        return id != null && id.equals(((Invoice) o).id);
    }
    @Override
    public int hashCode() {
        return 33;
    }

}
