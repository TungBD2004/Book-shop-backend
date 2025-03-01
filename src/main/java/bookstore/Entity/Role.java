package bookstore.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 254)
    @Column(name = "name")
    private String name;

    @Size(max = 254)
    @Column(name = "description")
    private String description;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "role")
    @JsonManagedReference
    private List<UserRole> userRoles = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "role")
    @JsonManagedReference
    private List<MenuRole> menuRoles = new ArrayList<>();
}
