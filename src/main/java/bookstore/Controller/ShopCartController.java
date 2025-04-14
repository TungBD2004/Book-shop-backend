package bookstore.Controller;

import bookstore.DTO.ShopCart.ProductShopCartDTO;
import bookstore.Entity.ShopCart;
import bookstore.Exception.BookShopAuthenticationException;
import bookstore.Exception.Constant.BSResponseEntity;
import bookstore.Exception.Constant.ErrorCode;
import bookstore.Exception.Constant.ErrorMessage;
import bookstore.Service.ShopCartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class ShopCartController {
    private final ShopCartService shopCartService;
    public ShopCartController(ShopCartService shopCartService) {
        this.shopCartService = shopCartService;
    }

    @PostMapping(value = "/shopcart")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN', 'USER')")
    public ResponseEntity<BSResponseEntity> addProductToShopCart(@RequestBody ProductShopCartDTO dto
                                                                 ) {
        BSResponseEntity ert = new BSResponseEntity();
        try{
            ert.setObject(shopCartService.addProductToShopCart(dto.getProductId()));
            ert.setMessage(ErrorMessage.ShopCart.ADD_PRODUCT_TO_SHOPCART_SUCCESS);
            ert.setCode(ErrorCode.CODE_SUCCESS);
        }
        catch (BookShopAuthenticationException e){
            ert.setCode(ErrorCode.CODE_ERROR);
            ert.setMessage(e.getErrMessage());
        }
        return ResponseEntity.ok(ert);
    }

    @DeleteMapping(value = "/shopcart/{id}")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN', 'USER')")
    public ResponseEntity<BSResponseEntity> removeProductFrom (@PathVariable Long id) {
        BSResponseEntity ert = new BSResponseEntity();
        try{
            ert.setObject(shopCartService.removeProductFromShopCart(id));
            ert.setMessage(ErrorMessage.ShopCart.REMOVE_PRODUCT_FROM_SHOPCART_SUCCESS);
            ert.setCode(ErrorCode.CODE_SUCCESS);
        }
        catch (BookShopAuthenticationException e){
            ert.setCode(ErrorCode.CODE_ERROR);
            ert.setMessage(e.getErrMessage());
        }
        return ResponseEntity.ok(ert);
    }

    @GetMapping("/shopcart")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN', 'USER')")
    public ResponseEntity<BSResponseEntity> getShopCartByUser() {
        BSResponseEntity ert = new BSResponseEntity();
        try{
            ert.setObject(shopCartService.getShopCartByUser());
            ert.setMessage(ErrorMessage.Common.SUCCESS);
            ert.setCode(ErrorCode.CODE_SUCCESS);
        }
        catch (BookShopAuthenticationException e){
            ert.setCode(ErrorCode.CODE_ERROR);
            ert.setMessage(e.getErrMessage());
        }
        return ResponseEntity.ok(ert);
    }




}
