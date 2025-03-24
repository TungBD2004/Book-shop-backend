package bookstore.Repository;

import bookstore.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByEmailIgnoreCase(String email);

    Optional<User> findOneByEmailIgnoreCase(String email);
}
