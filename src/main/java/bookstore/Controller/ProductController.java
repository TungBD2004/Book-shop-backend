package bookstore.Controller;

import bookstore.DTO.Product.ProductDTO;
import bookstore.DTO.Product.ProductDetailDTO;
import bookstore.Exception.BookShopAuthenticationException;
import bookstore.Exception.Constant.BSResponseEntity;
import bookstore.Exception.Constant.ErrorCode;
import bookstore.Exception.Constant.ErrorMessage;
import bookstore.Exception.DataInvalidException;
import bookstore.Exception.DataNotFoundException;
import bookstore.Request.ProductRequest.ProductDetailRequest;
import bookstore.Service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
        catch(DataInvalidException e){
            ert.setCode(ErrorCode.CODE_ERROR);
            ert.setMessage(e.getErrMessage());
            return ResponseEntity.badRequest().body(ert);
        }
        catch (DataNotFoundException e){
            ert.setCode(ErrorCode.CODE_ERROR);
            ert.setMessage(e.getErrMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ert);
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
        catch(DataInvalidException e){
            ert.setCode(ErrorCode.CODE_ERROR);
            ert.setMessage(e.getErrMessage());
            return ResponseEntity.badRequest().body(ert);
        }
        catch (DataNotFoundException e){
            ert.setCode(ErrorCode.CODE_ERROR);
            ert.setMessage(e.getErrMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ert);
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
        catch(DataInvalidException e){
            ert.setCode(ErrorCode.CODE_ERROR);
            ert.setMessage(e.getErrMessage());
            return ResponseEntity.badRequest().body(ert);
        }
        catch (DataNotFoundException e){
            ert.setCode(ErrorCode.CODE_ERROR);
            ert.setMessage(e.getErrMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ert);
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
        catch(DataInvalidException e){
            ert.setCode(ErrorCode.CODE_ERROR);
            ert.setMessage(e.getErrMessage());
        }
        catch (DataNotFoundException e){
            ert.setCode(ErrorCode.CODE_ERROR);
            ert.setMessage(e.getErrMessage());
        }

        return ResponseEntity.ok().body(ert);
    }

    @PostMapping("/admin/product")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<BSResponseEntity> addProduct(@Valid @RequestBody ProductDetailRequest productDetailRequest) {
        BSResponseEntity ert = new BSResponseEntity();
        try{
            ert.setObject(productService.addProduct(productDetailRequest));
            ert.setCode(ErrorCode.CODE_SUCCESS);
            ert.setMessage(ErrorMessage.Product.UPDATE_PRODUCT_SUCCESS);
        }
        catch(DataInvalidException e){
            ert.setCode(ErrorCode.CODE_ERROR);
            ert.setMessage(e.getErrMessage());
            return ResponseEntity.badRequest().body(ert);
        }
        catch (DataNotFoundException e){
            ert.setCode(ErrorCode.CODE_ERROR);
            ert.setMessage(e.getErrMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ert);
        }
        return ResponseEntity.ok().body(ert);
    }

    @PutMapping("/admin/product")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<BSResponseEntity> updateProduct(@Valid @RequestBody ProductDetailRequest productDTO) {
        BSResponseEntity ert = new BSResponseEntity();
        try{
            ert.setObject(productService.updateProduct( productDTO));
            ert.setCode(ErrorCode.CODE_SUCCESS);
            ert.setMessage(ErrorMessage.Product.UPDATE_PRODUCT_SUCCESS);
        }
        catch(DataInvalidException e){
            ert.setCode(ErrorCode.CODE_ERROR);
            ert.setMessage(e.getErrMessage());
        }
        catch (DataNotFoundException e){
            ert.setCode(ErrorCode.CODE_ERROR);
            ert.setMessage(e.getErrMessage());
        }
        catch (Exception e){
            ert.setCode(ErrorCode.CODE_ERROR);
            ert.setMessage(e.getMessage());
        }
        return ResponseEntity.ok().body(ert);
    }

    @DeleteMapping("/admin/product/{id}")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<BSResponseEntity> deleteProduct(@PathVariable Long id ) {
        BSResponseEntity ert = new BSResponseEntity();
        try{
            ert.setObject(productService.deleteProduct(id));
            ert.setCode(ErrorCode.CODE_SUCCESS);
            ert.setMessage(ErrorMessage.Product.DELETE_PRODUCT_SUCCESS);
        }
        catch(DataInvalidException e){
            ert.setCode(ErrorCode.CODE_ERROR);
            ert.setMessage(e.getErrMessage());
        }
        catch (DataNotFoundException e){
            ert.setCode(ErrorCode.CODE_ERROR);
            ert.setMessage(e.getErrMessage());
        }
        return ResponseEntity.ok().body(ert);
    }

}
