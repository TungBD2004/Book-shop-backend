package bookstore.Repository;

import bookstore.Entity.Menu;
import bookstore.Entity.MenuRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRoleRepository extends JpaRepository<MenuRole, Long> {

    @Query(value = "SELECT m.id, m.name, m.parent_id, m.description FROM menu m " +
            "JOIN menu_role rhm ON m.id = rhm.menu_id " +
            "JOIN role r ON r.id = rhm.role_id " +
            "WHERE r.id = :id AND parent_id = :parentId " +
            "ORDER BY m.parent_id ASC, m.id ASC", nativeQuery = true)
    List<Menu> getListMenuByRolesAndParent(@Param("id") Long id, @Param("parentId") Long parentId);





}
