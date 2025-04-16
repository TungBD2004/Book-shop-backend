package bookstore.Request.ProductRequest;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailRequest {
    private Long id;

    @NotBlank(message = "Tên sản phẩm không được để trống")
    private String name;

    @Max(value = 1000000000, message = "Giá không được vượt quá 1 tỷ")
    private Long price;

    @Max(value = 10000, message = "Số lượng không được vượt quá 10.000")
    private Long quantity;

    @NotBlank(message = "Tác giả không được để trống")
    private String author;

    @NotBlank(message = "URL hình ảnh không được để trống")
    private String imageUrl;

    @NotBlank(message = "Mô tả không được để trống")
    private String description;

    @NotBlank(message = "Thể loại không được để trống")
    private String category;
}
