package bookstore.Service;

import bookstore.Entity.Role;
import bookstore.Entity.User;
import bookstore.Exception.Constant.ErrorCode;
import bookstore.Exception.Constant.ErrorMessage;
import bookstore.Exception.Constant.ErrorObject;
import bookstore.Exception.DataNotFoundException;
import bookstore.Repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {
    private RoleRepository roleRepository;
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    public List<Role> getRoleByUserId(Long id) {
        List<Role> roles = new ArrayList<>();
        roles = roleRepository.findRoleByUserId(id);
        return roles;
    }
    public Role getHighestRole(User user) {
        return roleRepository.findHighestRoleByUserId(user.getId()).orElseThrow(
                () -> new DataNotFoundException(ErrorMessage.User.USER_NOT_FOUND, ErrorCode.CODE_ERROR, ErrorObject.USER)
        );
    }
    public Role getRoleNameUser(Long id) {
        return roleRepository.findById(id).get();
    }
}
