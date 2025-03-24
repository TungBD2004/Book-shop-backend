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

    @Query(value = "select m.id,m.name,m.parent_id,m.description from menu m " +
            "join menu_role rhm on m.id=rhm.menu_id " +
            "join role r on r.id=rhm.role_id " +
            "where r.id=:id and parent_id =:parentId", nativeQuery = true)
    List<Menu> getListMenuByRolesAndParent(@Param("id") Long id,@Param("parentId") Long parentId);

    @Query(value = "select m.menu_id,m.menu_name,m.parent,m.menu_name_english from menu m " +
            "join role_has_menu rhm on m.menu_id=rhm.menu_id " +
            "join role r on r.role_id=rhm.role_id " +
            "where r.role_id=:roleId and parent =:parent", nativeQuery = true)
    List<Menu> findbyRoleidAndParent(@Param("roleId") Integer roleId,
                                     @Param("parent") Integer parent);


}
