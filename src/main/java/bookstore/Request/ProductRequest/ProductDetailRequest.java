package bookstore.Request.ProductRequest;

import jakarta.validation.constraints.*;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDetailRequest {
    private Long id;

    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(min = 3, max = 255, message = "Tên sản phẩm phải từ 3 đến 255 ký tự")
    @Pattern(regexp = "^[\\p{L}\\s]+$", message = "Không được chứa ký tự đặc biệt")
    private String name;

    @NotNull(message = "Giá không được để trống")
    @Min(value = 0, message = "Giá không được âm")
    @Max(value = 1_000_000_000, message = "Giá không được vượt quá 1 tỷ")
    private Long price;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 0, message = "Số lượng không được âm")
    @Max(value = 10_000, message = "Số lượng không được vượt quá 10.000")
    private Long quantity;

    @NotBlank(message = "Tác giả không được để trống")
    @Size(min = 3, max = 255, message = "Tên tác giả phải từ 3 đến 255 ký tự")
    @Pattern(regexp = "^[\\p{L}\\s]+$", message = "Tên tác giả không được chứa ký tự đặc biệt")
    private String author;


    private String imageUrl;

    @NotBlank(message = "Tác giả không được để trống")
    @Size(min = 3, max = 255, message = "Tên tác giả phải từ 3 đến 255 ký tự")
    @Pattern(regexp = "^[\\p{L}\\s]+$", message = "Thể loại không được chứa ký tự đặc biệt")
    private String category;
    private String description;
}
