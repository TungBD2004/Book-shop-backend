package bookstore.Request.BillRequest;

import lombok.Data;

@Data
public class UpdateBillStatusRequest {
    private Long id;
    private String status;
}
