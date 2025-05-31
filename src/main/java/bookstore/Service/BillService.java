package bookstore.Service;

import bookstore.DTO.Bill.BillDetailDTO;
import bookstore.DTO.Bill.CreateBillDTO;
import bookstore.DTO.Bill.GetBillDTO;
import bookstore.DTO.Product.ProductDetailDTO;
import bookstore.Entity.*;
import bookstore.Exception.Constant.ErrorCode;
import bookstore.Exception.Constant.ErrorMessage;
import bookstore.Exception.Constant.ErrorObject;
import bookstore.Exception.DataInvalidException;
import bookstore.Exception.DataNotFoundException;
import bookstore.Mapper.BillMapper;
import bookstore.Mapper.ProductMapper;
import bookstore.Mapper.UserMapper;
import bookstore.Repository.BillRepository;
import bookstore.Request.BillRequest.CreateBillRequest;
import bookstore.Util.BSUtil;
import bookstore.Util.Enum.BillStatusEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class BillService {
    private final ShopCartService shopCartService;
    private final ProductService productService;
    private final BillProductService billProductService;
    private final UserService userService;
    private final ProductMapper productMapper;
    private final BillMapper billMapper;
    private final UserMapper userMapper;
    LocalDate localDate = LocalDate.now();
    private final BillRepository billRepository;
    private final BSUtil bsUtil;
    public BillService(BillRepository billRepository, BSUtil bsUtil, ShopCartService shopCartService, ProductService productService, BillProductService billProductService, UserService userService, ProductMapper productMapper, BillMapper billMapper, UserMapper userMapper) {
        this.billRepository = billRepository;
        this.bsUtil = bsUtil;
        this.shopCartService = shopCartService;
        this.productService = productService;
        this.billProductService = billProductService;
        this.userService = userService;
        this.productMapper = productMapper;
        this.billMapper = billMapper;
        this.userMapper = userMapper;
    }
    @Transactional
    public Object addBill(CreateBillRequest createBillRequest) {
        Bill bill = new Bill();
        bill.setAddress(createBillRequest.getAddress().trim().replaceAll("\\s+", " "));
        bill.setPhoneNumber(createBillRequest.getPhoneNumber());
        bill.setDate(java.sql.Date.valueOf(localDate));
        bill.setUser(bsUtil.getCurrentUserLogin());
        bill.setTotalPrice(createBillRequest.getTotalPrice());
        bill.setStatus(String.valueOf(BillStatusEnum.PENDING));
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
    public Object getAllBillByAdmin(){

        List<Bill> bills = billRepository.findAll();
        List<GetBillDTO> getBillDTOs = new ArrayList<>();
        for(Bill bill : bills){
            User user = bill.getUser();
            GetBillDTO getBillDTO = billMapper.toGetBillDTO(bill);
            getBillDTO.setUser(userMapper.UserToUserDTO(user));
            getBillDTOs.add(getBillDTO);
        }
        return getBillDTOs;
    }

    public Object getBillBySearchType(String email,String fromDate,String toDate,String status) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date from = null;
        Date to = null;
        if (fromDate != null && !fromDate.trim().isEmpty()) {
            from = sdf.parse(fromDate.trim());
        }
        if (toDate != null && !toDate.trim().isEmpty()) {
            to = sdf.parse(toDate.trim());
        }
        List<Bill> bills = billRepository.findBySearchType(email,from,to,status);
        List<GetBillDTO> getBillDTOs = new ArrayList<>();
        for(Bill bill : bills){
            User user = bill.getUser();
            GetBillDTO getBillDTO = billMapper.toGetBillDTO(bill);
            getBillDTO.setUser(userMapper.UserToUserDTO(user));
            getBillDTOs.add(getBillDTO);
        }
        return getBillDTOs;
    }

    public Object getBillDetail(Long id) {
        Bill bill = billRepository.findById(id).orElseThrow(() ->
                new DataNotFoundException(ErrorMessage.Bill.BILL_NOT_FOUND,ErrorCode.CODE_ERROR,ErrorObject.BILL ));
        BillDetailDTO billDetailDTO = new BillDetailDTO();
        billDetailDTO.setId(bill.getId());
        billDetailDTO.setAddress(bill.getAddress());
        billDetailDTO.setPhoneNumber(bill.getPhoneNumber());
        billDetailDTO.setTotalPrice(bill.getTotalPrice());
        billDetailDTO.setStatus(bill.getStatus());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        billDetailDTO.setDate(sdf.format(bill.getDate()));
        billDetailDTO.setDescription(bill.getDescription());
        List<BillProduct> billProducts = bill.getBillProducts();
        List<ProductDetailDTO> productDetailDTOs = new ArrayList<>();
        for(BillProduct billProduct : billProducts){
            Product product = productService.findProductAllById(billProduct.getProduct().getId());
            ProductDetailDTO productDetailDTO = new ProductDetailDTO();
            productDetailDTO = productMapper.toProductDTO(product);
            productDetailDTO.setQuantity(billProduct.getQuantity());
            productDetailDTOs.add(productDetailDTO);
        }
        billDetailDTO.setUser(userMapper.UserToUserDTO(bill.getUser()));
        billDetailDTO.setProducts(productDetailDTOs);
        return billDetailDTO;
    }

    public List<Bill> getBillByUser(Long userId){
        return billRepository.findBillByUserId(userId);
    }

    @Transactional
    public void updateStatus(Long id,String newStatus){
        Bill bill = billRepository.findById(id).orElseThrow(() ->
                new DataNotFoundException(ErrorMessage.Bill.BILL_NOT_FOUND,ErrorCode.CODE_ERROR,ErrorObject.BILL));

        switch (bill.getStatus()) {
            case "Đang giao":
                if (Objects.equals(newStatus, "Chờ xử lý") || Objects.equals(newStatus, "Đã hủy")) {
                    throw new IllegalStateException("Không thể chuyển từ Đang giao sang trạng thái này.");
                }
                break;
            case "Đã giao":
                if(!Objects.equals(newStatus, "Đã giao")){
                    throw new IllegalStateException("Đơn hàng đã giao không được thay đổi trạng thái.");
                }
                break;
            case "Chờ xử lý":
                if (Objects.equals(newStatus, "Đã giao")) {
                    throw new IllegalStateException("Không thể chuyển từ Chờ xử lý sang Đã giao trực tiếp.");
                }
                break;
            case "Đã hủy":
                if(!Objects.equals(newStatus, "Đã hủy")){
                    throw new IllegalStateException("Đơn hàng đã hủy không được thay đổi trạng thái.");
                }
                break;
        }
        bill.setStatus(newStatus);
        billRepository.save(bill);
    }

}
