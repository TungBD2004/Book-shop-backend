package bookstore.Service;

import bookstore.DTO.Bill.BillDetailDTO;
import bookstore.DTO.Bill.CreateBillDTO;
import bookstore.DTO.Product.ProductDetailDTO;
import bookstore.Entity.*;
import bookstore.Exception.Constant.ErrorCode;
import bookstore.Exception.Constant.ErrorMessage;
import bookstore.Exception.Constant.ErrorObject;
import bookstore.Exception.DataInvalidException;
import bookstore.Exception.DataNotFoundException;
import bookstore.Mapper.ProductMapper;
import bookstore.Repository.BillRepository;
import bookstore.Request.BillRequest.CreateBillRequest;
import bookstore.Util.BSUtil;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
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
    private final ProductMapper productMapper;
    LocalDate localDate = LocalDate.now();
    private final BillRepository billRepository;
    private final BSUtil bsUtil;
    public BillService(BillRepository billRepository, BSUtil bsUtil, ShopCartService shopCartService, ProductService productService, BillProductService billProductService, UserService userService, ProductMapper productMapper) {
        this.billRepository = billRepository;
        this.bsUtil = bsUtil;
        this.shopCartService = shopCartService;
        this.productService = productService;
        this.billProductService = billProductService;
        this.userService = userService;
        this.productMapper = productMapper;
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

    public Object getBillDetail(Long id) {
        Bill bill = billRepository.findById(id).orElseThrow(() ->
                new DataNotFoundException(ErrorMessage.Bill.BILL_NOT_FOUND,ErrorCode.CODE_ERROR,ErrorObject.BILL ));
        BillDetailDTO billDetailDTO = new BillDetailDTO();
        billDetailDTO.setId(bill.getId());
        billDetailDTO.setAddress(bill.getAddress());
        billDetailDTO.setPhoneNumber(bill.getPhoneNumber());
        billDetailDTO.setTotalPrice(bill.getTotalPrice());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        billDetailDTO.setDate(sdf.format(bill.getDate()));
        billDetailDTO.setDescription(bill.getDescription());
        List<BillProduct> billProducts = bill.getBillProducts();
        List<ProductDetailDTO> productDetailDTOs = new ArrayList<>();
        for(BillProduct billProduct : billProducts){
            Product product = productService.getById(billProduct.getProduct().getId());
            ProductDetailDTO productDetailDTO = new ProductDetailDTO();
            productDetailDTO = productMapper.toProductDTO(product);
            productDetailDTO.setQuantity(billProduct.getQuantity());
            productDetailDTOs.add(productDetailDTO);
        }
        billDetailDTO.setProducts(productDetailDTOs);
        return billDetailDTO;
    }

    public List<Bill> getBillByUser(Long userId){
        return billRepository.findBillByUserId(userId);
    }

}
