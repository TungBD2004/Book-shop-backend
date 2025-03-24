package bookstore.DTO;

import bookstore.Entity.Menu;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class MenuDTO {
    Long id;
    String name;
    Set<MenuDTO> children;
}
