package bookstore.DTO.Bill;

import bookstore.DTO.Product.ProductDetailDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateBillDTO {
    private List<ProductDetailDTO> products;
    private String address;
    private String phoneNumber;
    private Long totalPrice;
}
