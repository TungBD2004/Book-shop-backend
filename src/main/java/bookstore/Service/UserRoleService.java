package bookstore.Service;

import bookstore.Entity.Role;
import bookstore.Entity.UserRole;
import bookstore.Repository.UserRoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserRoleService {
    private final UserRoleRepository userRoleRepository;
    public UserRoleService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    public List<String> getNameRoleByUserId(Long id) {
        List<String> roles = new ArrayList<>();
        roles = userRoleRepository.findNameRoleByUserId(id);
        return roles;
    }
    public void save(UserRole userRole) {
        userRoleRepository.save(userRole);
    }


    public void delete(List<UserRole> userRole) {
        userRoleRepository.deleteAll(userRole);
    }
}
