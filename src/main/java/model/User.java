package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name="User")
@Table(name = "users")
public class User {

    @Id
    private String username;
    private String password;
    private Boolean enabled;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Unit> units = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        return username != null && username.equals(((User) o).username);
    }

    public void addUnit(Unit unit) {
        units.add(unit);
        unit.setUser(this);
    }

    public void removeUnit(Unit unit) {
        units.remove(unit);
        unit.setUser(null);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public String toString() {
        return "User{" +
                "userame='" + username + '\'' +
                ", name='" + firstName +" "+ lastName + '\'' +
                ", enabled='" + enabled + '\'' +
                ", units= [" + units.toString() +" ] }";
    }

}
