package bookstore.Controller;

import bookstore.DTO.MenuDTO;
import bookstore.Entity.Menu;
import bookstore.Service.MenuRoleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuRoleController {
    private MenuRoleService menuRoleService;

    public MenuRoleController(MenuRoleService menuRoleService) {
        this.menuRoleService = menuRoleService;
    }

   /* @GetMapping("/get-menu/{id}")
    public List<MenuDTO> getMenu(@PathVariable Long id) {
        return menuRoleService.getMenuRoles(id);
    }*/
}
