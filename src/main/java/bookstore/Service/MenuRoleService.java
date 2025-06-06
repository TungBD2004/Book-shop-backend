package bookstore.Service;

import bookstore.DTO.MenuDTO;
import bookstore.Entity.Menu;
import bookstore.Entity.Role;
import bookstore.Entity.User;
import bookstore.Mapper.MenuMapper;
import bookstore.Repository.MenuRoleRepository;
import bookstore.Util.BSUtil;
import bookstore.Util.Enum.RoleEnum;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MenuRoleService {
    private final MenuRoleRepository menuRoleRepository;
    private final MenuMapper menuMapper;
    private final RoleService roleService;
    private final BSUtil bsUtil;
    private final UserService userService;

    public MenuRoleService(MenuRoleRepository menuRoleRepository, MenuMapper menuMapper, RoleService roleService, BSUtil bsUtil, UserService userService) {
        this.menuRoleRepository = menuRoleRepository;
        this.menuMapper = menuMapper;
        this.roleService = roleService;
        this.bsUtil = bsUtil;
        this.userService = userService;
    }

    public Set<MenuDTO> getMenuDTO(List<Role> roles, Long parentId) {
        Set<Menu> menuSet = new HashSet<>();
        for (Role roleItem : roles) {
            List<Menu> menu = menuRoleRepository.getListMenuByRolesAndParent(roleItem.getId(), parentId);
            menuSet.addAll(menu);
        }

        // TreeSet để tự động sắp xếp theo id
        Set<MenuDTO> menuDTOs = new TreeSet<>(Comparator.comparingLong(MenuDTO::getId));
        for (Menu menuItem : menuSet) {
            MenuDTO dto = menuMapper.menuToMenuDTO(menuItem);
            // Đệ quy children cũng dùng TreeSet
            dto.setChildren(getMenuDTO(roles, dto.getId()));
            menuDTOs.add(dto);
        }

        return menuDTOs;
    }

    public Object getMenuHomepage() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentLoginUser = null;

        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            String email = auth.getName();
            currentLoginUser = userService.findByEmail(email);
        }

        Role role;
        if (currentLoginUser != null) {
            role = roleService.getHighestRole(currentLoginUser);
        } else {
            role = roleService.getRoleByName(RoleEnum.USER.name());
        }

        List<Role> roles = new ArrayList<>();
        roles.add(role);

        Set<MenuDTO> menuSet = getMenuDTO(roles, 26L);

        return Map.of(
                "menu", menuSet
        );
    }


}
