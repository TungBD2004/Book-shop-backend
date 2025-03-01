package bookstore.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @Size(min = 5, max = 254)
    @Column(length = 254, unique = true, nullable = false)
    private String email;

    @Column(name = "password_hash")
    private String password;

    @Size(max = 50)
    @Column(name = "name")
    private String name;

    @Size(max = 254)
    @Column(name = "address")
    private String address;

    @Size(max = 254)
    @Column(name = "description")
    private String desscription;


}
