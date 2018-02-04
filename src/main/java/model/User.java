package model;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import util.Util;

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
    private String name;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Unit2> units2 = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        return username != null && username.equals(((User) o).username);
    }

    public void addUnit2(Unit2 unit2) {
        units2.add(unit2);
        unit2.setUser(this);
    }

    public void removeUnit2(Unit2 unit2) {
        units2.remove(unit2);
        unit2.setUser(null);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + username + '\'' +
                ", first_name='" + name + '\'' +
                ", enabled='" + enabled + '\'' +
                ", password='" + password + '\'' +
                ", units= [" + units2.toString() +" ] }";
    }
}
