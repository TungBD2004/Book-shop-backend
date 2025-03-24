package bookstore.Service;

import bookstore.DTO.MenuDTO;
import bookstore.Entity.Menu;
import bookstore.Entity.MenuRole;
import bookstore.Entity.Role;
import bookstore.Mapper.MenuMapper;
import bookstore.Repository.MenuRoleRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MenuRoleService {
    private MenuRoleRepository menuRoleRepository;
    private MenuMapper menuMapper;
    private MenuService menuService;
    public MenuRoleService(MenuRoleRepository menuRoleRepository, MenuMapper menuMapper, MenuService menuService) {
        this.menuRoleRepository = menuRoleRepository;
        this.menuMapper = menuMapper;
        this.menuService = menuService;
    }

    public Set<MenuDTO> getMenuDTO(List<Role> role,Long parentId) {
        Set<Menu> menuSet = new HashSet<>();
        for(Role roleItem : role) {
           List<Menu> menu = menuRoleRepository.getListMenuByRolesAndParent(roleItem.getId(), parentId);
           menuSet.addAll(menu);
        }
        Set<MenuDTO> menuDTOs = new HashSet<>();
        for(Menu menuItem : menuSet) {
            menuDTOs.add(menuMapper.menuToMenuDTO(menuItem));
        }
        for(MenuDTO menuDTO : menuDTOs) {
            menuDTO.setChildren(getMenuDTO(role,menuDTO.getId()));
        }
        return menuDTOs;
    }

}
