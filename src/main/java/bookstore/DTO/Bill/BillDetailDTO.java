package bookstore.DTO.Bill;

import bookstore.DTO.Product.ProductDetailDTO;
import lombok.Data;

import java.util.List;

@Data
public class BillDetailDTO {
    private Long id;
    private String date;
    private Long totalPrice;
    private String address;
    private String description;
    private String phoneNumber;
    List<ProductDetailDTO> products;

}
