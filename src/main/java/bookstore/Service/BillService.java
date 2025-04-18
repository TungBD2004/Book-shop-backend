package bookstore.Service;

import bookstore.DTO.Bill.CreateBillDTO;
import bookstore.DTO.Product.ProductDetailDTO;
import bookstore.Entity.Bill;
import bookstore.Entity.BillProduct;
import bookstore.Entity.Product;
import bookstore.Entity.ShopCart;
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
    LocalDate localDate = LocalDate.now();
    private final BillRepository billRepository;
    private final BSUtil bsUtil;
    public BillService(BillRepository billRepository, BSUtil bsUtil, ShopCartService shopCartService, ProductService productService, BillProductService billProductService) {
        this.billRepository = billRepository;
        this.bsUtil = bsUtil;
        this.shopCartService = shopCartService;
        this.productService = productService;
        this.billProductService = billProductService;
    }

    public Object addBill(CreateBillRequest createBillRequest) {
        Bill bill = new Bill();
        bill.setAddress(createBillRequest.getAddress());
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
}
