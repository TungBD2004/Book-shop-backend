package bookstore.Controller;

import bookstore.Exception.BookShopAuthenticationException;
import bookstore.Exception.Constant.BSResponseEntity;
import bookstore.Exception.Constant.ErrorCode;
import bookstore.Exception.Constant.ErrorMessage;
import bookstore.Service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<BSResponseEntity> getProduct(@PathVariable Long id) {
        BSResponseEntity ert = new BSResponseEntity();
        try{
            ert.setObject(productService.getProductById(id));
            ert.setCode(ErrorCode.CODE_SUCCESS);
            ert.setMessage(ErrorMessage.Common.SUCCESS);
        }
        catch(BookShopAuthenticationException e){
            ert.setCode(ErrorCode.CODE_ERROR);
            ert.setMessage(e.getErrMessage());
        }
        return ResponseEntity.ok().body(ert);
    }
    @GetMapping("/product")
    public ResponseEntity<BSResponseEntity> getListProductByName(@RequestParam String name) {
        BSResponseEntity ert = new BSResponseEntity();
        try{
            ert.setObject(productService.getProductByName(name));
            ert.setCode(ErrorCode.CODE_SUCCESS);
            ert.setMessage(ErrorMessage.Common.SUCCESS);
        }
        catch(BookShopAuthenticationException e){
            ert.setCode(ErrorCode.CODE_ERROR);
            ert.setMessage(e.getErrMessage());
        }
        return ResponseEntity.ok().body(ert);
    }
    @GetMapping("/product/home")
    public ResponseEntity<BSResponseEntity> getListProductByMenu() {
        BSResponseEntity ert = new BSResponseEntity();
        try{
            ert.setObject(productService.getProductMenu());
            ert.setCode(ErrorCode.CODE_SUCCESS);
            ert.setMessage(ErrorMessage.Common.SUCCESS);
        }
        catch(BookShopAuthenticationException e){
            ert.setCode(ErrorCode.CODE_ERROR);
            ert.setMessage(e.getErrMessage());
        }
        return ResponseEntity.ok().body(ert);

    }

    @GetMapping("/product/category")
    public ResponseEntity<BSResponseEntity> getProductByCategory(@RequestParam String categoryName) {
        BSResponseEntity ert = new BSResponseEntity();
        try{
            ert.setObject(productService.getProductByCategory(categoryName));
            ert.setCode(ErrorCode.CODE_SUCCESS);
            ert.setMessage(ErrorMessage.Common.SUCCESS);
        }
        catch(BookShopAuthenticationException e){
            ert.setCode(ErrorCode.CODE_ERROR);
            ert.setMessage(e.getErrMessage());
        }
        return ResponseEntity.ok().body(ert);
    }

}
