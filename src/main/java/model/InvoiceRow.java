package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "InvoiceRow")
@Table(name = "invoicerow")
public class InvoiceRow {

    @Id
    @SequenceGenerator(name = "my_seq4", sequenceName = "seq5", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_seq4")
    private Long id;
    private String title;
    private String description;
    private Double quantity;
    private Double unitPrice;
    @Transient
    private Double sumPrice;
    private Double taxAmount;
    private String ownerRef;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id")
    @JsonIgnore
    private Invoice invoice;

    public Double getSumPrice() {
        return quantity*unitPrice;
    }
}
