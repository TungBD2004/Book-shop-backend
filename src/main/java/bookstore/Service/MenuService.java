package bookstore.Service;

import bookstore.Entity.Menu;
import bookstore.Repository.MenuRepository;
import org.springframework.stereotype.Service;

@Service
public class MenuService {
    private MenuRepository menuRepository;
    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

}
