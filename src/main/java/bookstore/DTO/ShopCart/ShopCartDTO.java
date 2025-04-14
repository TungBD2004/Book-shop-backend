package bookstore.DTO.ShopCart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShopCartDTO {
    private Long id;
    private Long userId;
    private Long productId;
    private String description;
}
