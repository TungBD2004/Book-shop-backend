package bookstore.Util;

import bookstore.Entity.User;
import bookstore.Repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class BSUtil {
    private final UserRepository userRepository;
    public BSUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getCurrentUserLogin(){
        String currentUserStr = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmailIgnoreCase(currentUserStr);
    }
}
