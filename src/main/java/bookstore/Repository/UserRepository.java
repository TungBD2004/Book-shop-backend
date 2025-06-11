package bookstore.Repository;

import bookstore.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select u from User u where u.email = :email and u.isDelete = FALSE and u.isActive = TRUE")
    User findByEmailIgnoreCase(String email);

    @Query("select u from User u where u.isDelete = FALSE and u.isActive = TRUE")
    List<User> findAllUsers();

    @Query("SELECT u FROM User u WHERE u.email LIKE %:email% and u.isDelete = false and u.isActive = TRUE")
    List<User> findByEmail(@Param("email") String email);
}
