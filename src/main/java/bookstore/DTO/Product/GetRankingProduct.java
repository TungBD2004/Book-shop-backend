package bookstore.DTO.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetRankingProduct {
    private ProductDetailDTO product;
    private Long sellingQuantity;
}
