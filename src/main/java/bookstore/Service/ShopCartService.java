package bookstore.Service;

import bookstore.DTO.Product.ProductDetailDTO;
import bookstore.Entity.Product;
import bookstore.Entity.ShopCart;
import bookstore.Entity.User;
import bookstore.Exception.BookShopAuthenticationException;
import bookstore.Exception.Constant.ErrorCode;
import bookstore.Exception.Constant.ErrorMessage;
import bookstore.Exception.Constant.ErrorObject;
import bookstore.Exception.DataInvalidException;
import bookstore.Exception.DataNotFoundException;
import bookstore.Mapper.ProductMapper;
import bookstore.Mapper.ShopCartMapper;
import bookstore.Repository.ShopCartRepository;
import bookstore.Util.BSUtil;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShopCartService {
    private final ShopCartRepository shopCartRepository;
    private final BSUtil bsUtil;
    private final ProductService productService;
    private final ShopCartMapper shopCartMapper;
    private final ProductMapper productMapper;

    public ShopCartService(ShopCartRepository shopCartRepository, BSUtil bsUtil, ProductService productService, ShopCartMapper shopCartMapper, ProductMapper productMapper) {
        this.shopCartRepository = shopCartRepository;
        this.bsUtil = bsUtil;
        this.productService = productService;
        this.shopCartMapper = shopCartMapper;
        this.productMapper = productMapper;
    }

    @Transactional
    public Object addProductToShopCart(Long productId) {
        User currentUser = bsUtil.getCurrentUserLogin();
        ShopCart shopCart = shopCartRepository.findByUserIdAndProductId(currentUser.getId(), productId);
        if(shopCart != null) {
            throw new DataInvalidException(ErrorMessage.ShopCart.SHOPCART_ALREADY_EXISTED,ErrorCode.CODE_ERROR,ErrorObject.SHOPCART);
        }
        Product product = productService.getById(productId);
        shopCart = new ShopCart();
        shopCart.setUser(currentUser);
        shopCart.setProduct(product);
        shopCart.setQuantity(1L);
        shopCartRepository.save(shopCart);
        return shopCartMapper.toShopCartDTO(shopCart);
    }

    public Object removeProductFromShopCart(Long id) {
        User currentUser = bsUtil.getCurrentUserLogin();
        ShopCart shopCart = shopCartRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException(ErrorMessage.ShopCart.SHOPCART_NOT_FOUND, ErrorCode.CODE_ERROR, ErrorObject.SHOPCART)
        );
        shopCartRepository.delete(shopCart);
        return null;
    }


    public Object getShopCartByUser() {
        User currentUser = bsUtil.getCurrentUserLogin();
        List<ShopCart> shopCarts = shopCartRepository.findByUserId(currentUser.getId());
        if(shopCarts.isEmpty()) {
            throw new DataNotFoundException(ErrorMessage.ShopCart.SHOPCART_IS_EMPTY, ErrorCode.CODE_ERROR, ErrorObject.SHOPCART);
        }
        List<ProductDetailDTO> detailDTOS = new ArrayList<>();
        for(ShopCart shopCart : shopCarts) {
            Product product = productService.getById(shopCart.getProduct().getId());
            ProductDetailDTO productDetailDTO = new ProductDetailDTO();
            productDetailDTO = productMapper.toProductDTO(product);
            productDetailDTO.setQuantity(shopCart.getQuantity());
            productDetailDTO.setCategory(product.getCategory().getName());
            productDetailDTO.setId(shopCart.getId());
            detailDTOS.add(productDetailDTO);
        }
        return detailDTOS;
    }
    public ShopCart getById(Long id){
        return shopCartRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException(ErrorMessage.ShopCart.SHOPCART_NOT_FOUND, ErrorCode.CODE_ERROR, ErrorObject.SHOPCART)
        );
    }
}
