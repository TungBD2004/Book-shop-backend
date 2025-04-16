package bookstore.Request.BillRequest;

import bookstore.DTO.Product.ProductDetailDTO;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import javax.validation.constraints.Size;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateBillRequest {
    private List<ProductDetailDTO> products;

    @NotBlank(message = "Địa chỉ không được để trống")
    private String address;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Size(min = 11, max = 11, message = "Số điện thoại phải có 11 ký tự")
    @Pattern(regexp = "^(0[0-9]{9})$", message = "Số điện thoại không hợp lệ")
    private String phoneNumber;

    @Max(value = 1000000000, message = "Tổng tiền không được vượt quá 1 tỷ")
    private Long totalPrice;

}
