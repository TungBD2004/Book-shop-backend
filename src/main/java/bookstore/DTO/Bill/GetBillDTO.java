package bookstore.DTO.Bill;

import bookstore.DTO.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetBillDTO {
    private Long id;
    private String date;
    private Long totalPrice;
    private String address;
    private String description;
    private String phoneNumber;
    private String status;
    private UserDTO user;
}
