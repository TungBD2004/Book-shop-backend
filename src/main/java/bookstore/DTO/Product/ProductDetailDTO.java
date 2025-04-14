package bookstore.DTO.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDetailDTO {
    private Long id;
    private String name;
    private Long price;
    private Long quantity;
    private String author;
    private String imageUrl;
    private String description;
    private String category;
}
