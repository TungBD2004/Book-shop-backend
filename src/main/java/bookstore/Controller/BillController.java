package bookstore.Controller;

import bookstore.DTO.Bill.CreateBillDTO;
import bookstore.Entity.Bill;
import bookstore.Exception.BookShopAuthenticationException;
import bookstore.Exception.Constant.BSResponseEntity;
import bookstore.Exception.Constant.ErrorCode;
import bookstore.Exception.Constant.ErrorMessage;
import bookstore.Exception.DataInvalidException;
import bookstore.Request.BillRequest.CreateBillRequest;
import bookstore.Service.BillService;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/")
public class BillController {
    private final BillService billService;
    public BillController(BillService billService) {
        this.billService = billService;
    }

    @PostMapping("/bill")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN', 'USER')")
    public ResponseEntity<BSResponseEntity> addBill(@Valid @RequestBody CreateBillRequest createBillRequest) {
        BSResponseEntity responseEntity = new BSResponseEntity();
        try {
            responseEntity.setObject(billService.addBill(createBillRequest));
            responseEntity.setCode(ErrorCode.CODE_SUCCESS);
            responseEntity.setMessage(ErrorMessage.Bill.ORDER_SUCCESS);
        }
        catch (DataInvalidException e){
            responseEntity.setCode(ErrorCode.CODE_ERROR);
            responseEntity.setMessage(e.getErrMessage());
        }
        catch (Exception ex) {
            responseEntity.setCode(ErrorCode.CODE_ERROR);
            responseEntity.setMessage(ex.getMessage());
        }

        return ResponseEntity.ok().body(responseEntity);
    }


}
