package bookstore.Mapper;

import bookstore.DTO.Bill.GetBillDTO;
import bookstore.Entity.Bill;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class BillMapper {

    public GetBillDTO toGetBillDTO(Bill bill) {
        GetBillDTO getBillDTO = new GetBillDTO();

        getBillDTO.setId(bill.getId());

        // Format Date -> String dạng dd/MM/yy
        Date billDate = bill.getDate();
        if (billDate != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
            getBillDTO.setDate(sdf.format(billDate));
        } else {
            getBillDTO.setDate(null); // hoặc ""
        }

        getBillDTO.setAddress(bill.getAddress());
        getBillDTO.setDescription(bill.getDescription());
        getBillDTO.setPhoneNumber(bill.getPhoneNumber());
        getBillDTO.setTotalPrice(bill.getTotalPrice());

        return getBillDTO;
    }
}
