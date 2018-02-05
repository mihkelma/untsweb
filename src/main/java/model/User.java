package model;

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
    private List<Unit> units2 = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        return username != null && username.equals(((User) o).username);
    }

    public void addUnit2(Unit unit) {

        units2.add(unit);
        unit.setUser(this);
    }

    public void removeUnit2(Unit unit) {
        units2.remove(unit);
        unit.setUser(null);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + username + '\'' +
                ", name='" + firstName +" "+ lastName + '\'' +
                ", enabled='" + enabled + '\'' +
                ", password='" + password + '\'' +
                ", units= [" + units2.toString() +" ] }";
    }
}
