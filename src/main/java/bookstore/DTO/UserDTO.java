package bookstore.DTO;

import bookstore.Entity.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String email;
    private String name;
    private String address;
}
