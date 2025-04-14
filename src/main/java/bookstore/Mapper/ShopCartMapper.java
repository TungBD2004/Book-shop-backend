package bookstore.Mapper;

import bookstore.DTO.ShopCart.ShopCartDTO;
import bookstore.Entity.ShopCart;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
public class ShopCartMapper {
    public ShopCartDTO toShopCartDTO(ShopCart shopCart) {
        ShopCartDTO shopCartDTO = new ShopCartDTO();
        shopCartDTO.setId(shopCart.getId());
        shopCartDTO.setDescription(shopCart.getDescription());
        shopCartDTO.setUserId(shopCart.getUser().getId());
        shopCartDTO.setProductId(shopCart.getProduct().getId());
        return shopCartDTO;
    }
}
