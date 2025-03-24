package bookstore.Mapper;

import bookstore.DTO.MenuDTO;
import bookstore.Entity.Menu;
import org.springframework.stereotype.Service;

@Service
public class MenuMapper {
    public MenuDTO menuToMenuDTO(Menu menu) {
        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setId(menu.getId());
        menuDTO.setName(menu.getName());
        return menuDTO;
    }

}
