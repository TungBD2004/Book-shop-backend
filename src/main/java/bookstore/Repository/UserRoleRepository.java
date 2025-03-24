package bookstore.Repository;

import bookstore.Entity.Role;
import bookstore.Entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    @Query(value = "select r.name from UserRole ur join ur.role r where ur.user.id = :id")
    List<String> findNameRoleByUserId(@Param("id") Long id);
}
