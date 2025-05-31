package bookstore.DTO.Bill;

import bookstore.DTO.Product.ProductDetailDTO;
import bookstore.DTO.UserDTO;
import bookstore.Util.Enum.BillStatusEnum;
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
    private String status;
    List<ProductDetailDTO> products;
    UserDTO user;
}
