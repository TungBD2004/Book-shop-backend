package bookstore.Service;

import bookstore.DTO.Bill.CreateBillDTO;
import bookstore.DTO.Product.ProductDetailDTO;
import bookstore.Entity.*;
import bookstore.Exception.Constant.ErrorCode;
import bookstore.Exception.Constant.ErrorMessage;
import bookstore.Exception.Constant.ErrorObject;
import bookstore.Exception.DataInvalidException;
import bookstore.Repository.BillRepository;
import bookstore.Request.BillRequest.CreateBillRequest;
import bookstore.Util.BSUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BillService {
    private final ShopCartService shopCartService;
    private final ProductService productService;
    private final BillProductService billProductService;
    private final UserService userService;
    LocalDate localDate = LocalDate.now();
    private final BillRepository billRepository;
    private final BSUtil bsUtil;
    public BillService(BillRepository billRepository, BSUtil bsUtil, ShopCartService shopCartService, ProductService productService, BillProductService billProductService, UserService userService) {
        this.billRepository = billRepository;
        this.bsUtil = bsUtil;
        this.shopCartService = shopCartService;
        this.productService = productService;
        this.billProductService = billProductService;
        this.userService = userService;
    }

    public Object addBill(CreateBillRequest createBillRequest) {
        Bill bill = new Bill();
        bill.setAddress(createBillRequest.getAddress().trim().replaceAll("\\s+", " "));
        bill.setPhoneNumber(createBillRequest.getPhoneNumber());
        bill.setDate(java.sql.Date.valueOf(localDate));
        bill.setUser(bsUtil.getCurrentUserLogin());
        bill.setTotalPrice(createBillRequest.getTotalPrice());
        billRepository.save(bill);
        List<BillProduct> billProducts = new ArrayList<>();
        for(ProductDetailDTO productDetailDTO : createBillRequest.getProducts()){
            BillProduct billProduct = new BillProduct();
            billProduct.setBill(bill);
            ShopCart shopCart = shopCartService.getById(productDetailDTO.getId());
            Product product = productService.getById(shopCart.getProduct().getId());
            if(productDetailDTO.getQuantity()>product.getQuantity()){
                throw new DataInvalidException(ErrorMessage.Product.PRODUCT_OUT_OF_QUANTITY, ErrorCode.CODE_ERROR, ErrorObject.PRODUCT);
            }
            product.setQuantity(product.getQuantity() - productDetailDTO.getQuantity());
            productService.save(product);
            billProduct.setProduct(product);
            billProduct.setPrice(productDetailDTO.getPrice());
            billProduct.setQuantity(productDetailDTO.getQuantity());
            billProducts.add(billProduct);
            shopCartService.removeProductFromShopCart(productDetailDTO.getId());
            billProductService.save(billProduct);
        }
        return null;
    }

    public List<Bill> getBillByUser(Long userId){
        return billRepository.findBillByUserId(userId);
    }

}
