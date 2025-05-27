package bookstore.Request.BillRequest;

import bookstore.DTO.Product.ProductDetailDTO;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import jakarta.validation.constraints.Size;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateBillRequest {
    private List<ProductDetailDTO> products;

    @NotBlank(message = "Địa chỉ không được để trống")
    @Size(min = 5,message = "Địa chỉ không hợp lệ")
    @Pattern(regexp = "^[\\p{L}0-9\\s,.-]+$", message = "Địa chỉ không được chứa ký tự đặc biệt")
    private String address;

    @NotBlank(message = "Số điện thoại không được để trống")
// Chấp nhận số điện thoại 10 chữ số, bắt đầu bằng 0
    @Pattern(regexp = "^0\\d{9}$", message = "Số điện thoại không hợp lệ")
    private String phoneNumber;

    @Max(value = 1000000000, message = "Tổng tiền không được vượt quá 1 tỷ")
    private Long totalPrice;

}
