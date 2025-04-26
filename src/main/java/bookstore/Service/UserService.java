package bookstore.Service;

import bookstore.DTO.UserDTO;
import bookstore.Entity.Role;
import bookstore.Entity.User;
import bookstore.Entity.UserRole;
import bookstore.Exception.Constant.ErrorCode;
import bookstore.Exception.Constant.ErrorMessage;
import bookstore.Exception.Constant.ErrorObject;
import bookstore.Exception.DataInvalidException;
import bookstore.Exception.DataNotFoundException;
import bookstore.Mapper.UserMapper;
import bookstore.Repository.UserRepository;
import bookstore.Request.UserRequest.ChangePasswordRequest;
import bookstore.Request.UserRequest.UpdateUserRequest;
import bookstore.Util.BSUtil;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    private final RoleService roleService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final BSUtil bsUtil;
    private final UserRoleService userRoleService;

    public UserService(UserRepository userRepository, UserMapper userMapper, RoleService roleService, PasswordEncoder passwordEncoder, BSUtil bsUtil, UserRoleService userRoleService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.bsUtil = bsUtil;
        this.userRoleService = userRoleService;
    }

    public User findByEmail(String email) {
        var user = userRepository.findByEmailIgnoreCase(email);
        return user;
    }
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new DataNotFoundException(ErrorMessage.User.USER_NOT_FOUND, ErrorCode.CODE_ERROR, ErrorObject.USER));

    }
    public Object getAllUser(){
        List<User> users = userRepository.findAllUsers();;
        List<UserDTO> dtos = new ArrayList<>();
        for(User user : users) {
            Role role = roleService.getHighestRole(user);
            if(role.getName().equals("USER") || role.getName().equals("ADMIN")) {
                dtos.add(userMapper.UserToUserDTO(user));
            }
        }
        return dtos;
    }
    public Object updateUser(Long id, UpdateUserRequest updateUserRequest) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new DataNotFoundException(ErrorMessage.User.USER_NOT_FOUND, ErrorCode.CODE_ERROR, ErrorObject.USER));
        user.setAddress(updateUserRequest.getAddress());
        user.setName(updateUserRequest.getName());
        userRepository.save(user);
        return user;
    }

    public Object deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new DataNotFoundException(ErrorMessage.User.USER_NOT_FOUND, ErrorCode.CODE_ERROR, ErrorObject.USER));
        List<UserRole> userRoles = user.getUserRoles();
        user.getUserRoles().clear();
        userRoleService.delete(userRoles);
        user.setIsDelete(true);
        userRepository.save(user);
        return null;
    }

    public Object changePassword(ChangePasswordRequest changePasswordRequest) {
        User user = bsUtil.getCurrentUserLogin();
        boolean isAuthenticated = passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword());
        if(!isAuthenticated){
            throw new DataInvalidException(ErrorMessage.User.PASSWORD_INCORRECT,ErrorCode.CODE_ERROR, ErrorObject.USER);
        }

        if(!Objects.equals(changePasswordRequest.getNewPassword(), changePasswordRequest.getConfirmPassword())) {
            throw new DataInvalidException(ErrorMessage.User.RE_PASSWORD_NOT_MATCH,ErrorCode.CODE_ERROR, ErrorObject.USER);
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        String encodedPassword = passwordEncoder.encode(changePasswordRequest.getNewPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return null;
    }
}
