package bookstore.Mapper;

import bookstore.DTO.UserDTO;
import bookstore.Entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public UserDTO UserToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setAddress(user.getAddress());
        return userDTO;
    }
}
