package bookstore.Service;

import bookstore.DTO.UserDTO;
import bookstore.Entity.User;
import bookstore.Exception.Constant.ErrorCode;
import bookstore.Exception.Constant.ErrorMessage;
import bookstore.Exception.Constant.ErrorObject;
import bookstore.Exception.DataNotFoundException;
import bookstore.Mapper.UserMapper;
import bookstore.Repository.UserRepository;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private UserMapper userMapper;
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public User findByEmail(String email) {
        var user = userRepository.findByEmailIgnoreCase(email);
        return user;
    }
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).get();
        if(user == null) {
            throw new DataNotFoundException(ErrorMessage.User.USER_NOT_FOUND, ErrorCode.CODE_ERROR, ErrorObject.USER);
        }
        return userMapper.UserToUserDTO(user);
    }
    public User getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return null;
    }
}
