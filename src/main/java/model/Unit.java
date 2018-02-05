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
@Entity(name = "Unit")
@Table(name = "unit")
public class Unit {

    @Id
    @SequenceGenerator(name = "my_seq", sequenceName = "seq1", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_seq")
    private Long id;
    private String name;
    private Double size;
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username")
    @JsonIgnore
    private User user;

    //TODO: how to make many to many with unit -< unit_contract >- contract so,
    // that unit_contract has status field which used to get only active contracts
    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Contract> contracts = new ArrayList<>();
    //TODO: how to add, merge and remove if using OO principles (on JPA side)

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Unit)) return false;
        return id != null && id.equals(((Unit) o).id);
    }
    @Override
    public int hashCode() {
        return 31;
    }
}
