package bookstore.Repository;

import bookstore.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query(value = "select r from UserRole ur join ur.role r where ur.user.id = :id")
    List<Role> findRoleByUserId(@Param("id") Long id);

    @Query("""
        SELECT r FROM Role r 
        JOIN UserRole ur ON ur.role.id = r.id 
        WHERE ur.user.id = :userId 
        ORDER BY 
            CASE 
                WHEN r.name = 'SUPER_ADMIN' THEN 1
                WHEN r.name = 'ADMIN' THEN 2
                WHEN r.name = 'USER' THEN 3
                ELSE 4
            END 
        ASC LIMIT 1
    """)
    Optional<Role> findHighestRoleByUserId(@Param("userId") Long userId);
}
