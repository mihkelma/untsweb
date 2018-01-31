package model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name="User")
@Table(name = "USERS")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="username")
public class User {
    @Id
    private String username;
    private String password;
    private Boolean enabled;
    private String name;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "userunit", joinColumns = @JoinColumn(name = "user_username"),
            inverseJoinColumns = @JoinColumn(name = "unit_id"))
    private List<Unit> units = new ArrayList<>();

    public void addUnit(Unit unit) {
        units.add(unit);
        unit.getUsers().add(this);
    }

    public void removeUnit(Unit unit) {
        units.remove(unit);
        unit.getUsers().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        return username != null && username.equals(((User) o).username);
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
                '}';
    }
}
