package bookstore.Service;

import bookstore.DTO.Bill.GetBillDTO;
import bookstore.DTO.UserDTO;
import bookstore.Entity.Bill;
import bookstore.Entity.Role;
import bookstore.Entity.User;
import bookstore.Entity.UserRole;
import bookstore.Exception.Constant.ErrorCode;
import bookstore.Exception.Constant.ErrorMessage;
import bookstore.Exception.Constant.ErrorObject;
import bookstore.Exception.DataInvalidException;
import bookstore.Exception.DataNotFoundException;
import bookstore.Mapper.BillMapper;
import bookstore.Mapper.UserMapper;
import bookstore.Repository.UserRepository;
import bookstore.Request.UserRequest.ChangePasswordRequest;
import bookstore.Request.UserRequest.UpdateUserRequest;
import bookstore.Util.BSUtil;
import bookstore.Util.Enum.RoleEnum;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class UserService {
    private final RoleService roleService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final BSUtil bsUtil;
    private final UserRoleService userRoleService;
    private final BillService billService;
    private final BillMapper billMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper, RoleService roleService, PasswordEncoder passwordEncoder, BSUtil bsUtil, UserRoleService userRoleService, @Lazy BillService billService, BillMapper billMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.bsUtil = bsUtil;
        this.userRoleService = userRoleService;
        this.billService = billService;
        this.billMapper = billMapper;
    }

    public User findByEmail(String email) {
        return  userRepository.findByEmailIgnoreCase(email);

    }
    public Object getAllUser(){
        List<User> users = userRepository.findAllUsers();
        List<UserDTO> dtos = new ArrayList<>();
        for(User user : users) {
            Role role = roleService.getHighestRole(user);
            UserDTO userDTO = userMapper.UserToUserDTO(user);
            userDTO.setRole(role.getName());
            if(role.getName().equals(RoleEnum.USER.name()) || role.getName().equals(RoleEnum.ADMIN.name())) {
                dtos.add(userDTO);
            }
        }
        return dtos;
    }
    @Transactional
    public Object updateUserByAdmin(Long id, UpdateUserRequest updateUserRequest) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new DataNotFoundException(ErrorMessage.User.USER_NOT_FOUND, ErrorCode.CODE_ERROR, ErrorObject.USER));
        user.setAddress(updateUserRequest.getAddress().trim().replaceAll("\\s+", " "));
        user.setName(updateUserRequest.getName().trim().replaceAll("\\s+", " "));
        userRepository.save(user);
        return user;
    }

    @Transactional
    public Object updateUserByOwner(UpdateUserRequest updateUserRequest) {
        User user = bsUtil.getCurrentUserLogin();
        user.setAddress(updateUserRequest.getAddress().trim().replaceAll("\\s+", " "));
        user.setName(updateUserRequest.getName().trim().replaceAll("\\s+", " "));
        userRepository.save(user);
        return user;
    }


    @Transactional
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

    @Transactional
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
    public Object getInfoByOwner(){
        User user = bsUtil.getCurrentUserLogin();
        List<Bill> bills = billService.getBillByUser(user.getId());
        List<GetBillDTO> billDTOS = new ArrayList<>();
        for(Bill bill : bills) {
            billDTOS.add(billMapper.toGetBillDTO(bill));
        }

        return Map.of("user", userMapper.UserToUserDTO(user),
                "bills" , billDTOS);
    }

    public Object getInfoByOwnerById(Long id){
        User user = userRepository.findById(id).orElseThrow(() ->
                new DataNotFoundException(ErrorMessage.User.USER_NOT_FOUND, ErrorCode.CODE_ERROR, ErrorObject.USER));
        List<Bill> bills = billService.getBillByUser(user.getId());
        List<GetBillDTO> billDTOS = new ArrayList<>();
        for(Bill bill : bills) {
            billDTOS.add(billMapper.toGetBillDTO(bill));
        }

        return Map.of("user", userMapper.UserToUserDTO(user),
                "bills" , billDTOS);
    }

    @Transactional
    public Object resetPassword(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new DataNotFoundException(ErrorMessage.User.USER_NOT_FOUND,ErrorCode.CODE_ERROR, ErrorObject.USER));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String newPassword = passwordEncoder.encode("12345");
        user.setPassword(newPassword);
        userRepository.save(user);
        return null;
    }

    @Transactional
    public Object addRoleAdmin(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new DataNotFoundException(ErrorMessage.User.USER_NOT_FOUND,ErrorCode.CODE_ERROR, ErrorObject.USER));
        Role role = roleService.getHighestRole(user);
        if(role.getName().equals(RoleEnum.ADMIN.name()) || role.getName().equals(RoleEnum.SUPER_ADMIN.name())) {
            throw new DataInvalidException(ErrorMessage.UserRole.USER_ALREADY_ADMIN,ErrorCode.CODE_ERROR, ErrorObject.USERROLE);
        }
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(roleService.getRoleByName(RoleEnum.ADMIN.name()));
        userRoleService.save(userRole);
        return null;
    }
    @Transactional
    public Object removeRoleAdmin(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new DataNotFoundException(ErrorMessage.User.USER_NOT_FOUND,ErrorCode.CODE_ERROR, ErrorObject.USER));
        Role role = roleService.getHighestRole(user);
        if(role.getName().equals(RoleEnum.USER.name()) || role.getName().equals(RoleEnum.SUPER_ADMIN.name())) {
            throw new DataInvalidException(ErrorMessage.UserRole.USER_NOT_ADMIN,ErrorCode.CODE_ERROR, ErrorObject.USERROLE);
        }
        List<UserRole> userRole = userRoleService.getUserRoleByUserId(id);
        userRoleService.delete(userRole);
        return null;
    }

    public Object findUsersByEmail(String email){
        List<User> users = userRepository.findByEmail(email);
        List<UserDTO> userDTOs = new ArrayList<>();
        for(User user : users) {
            Role role = roleService.getHighestRole(user);
            UserDTO userDTO = userMapper.UserToUserDTO(user);
            userDTO.setRole(role.getName());
            if(role.getName().equals(RoleEnum.USER.name()) || role.getName().equals(RoleEnum.ADMIN.name())) {
                userDTOs.add(userDTO);
            }
        }
        return userDTOs;
    }

}
