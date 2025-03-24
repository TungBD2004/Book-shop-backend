package bookstore.Security;

import bookstore.DTO.MenuDTO;
import bookstore.DTO.UserDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class JWTToken {
    private String idToken;
    List<String> roles;
    UserDTO userDTO;
    List<MenuDTO> menuDTO;

}
