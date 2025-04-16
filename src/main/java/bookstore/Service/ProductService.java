package bookstore.Service;

import bookstore.DTO.Product.ProductDetailDTO;
import bookstore.Entity.Product;
import bookstore.Exception.BookShopAuthenticationException;
import bookstore.Exception.Constant.ErrorCode;
import bookstore.Exception.Constant.ErrorMessage;
import bookstore.Exception.Constant.ErrorObject;
import bookstore.Exception.DataNotFoundException;
import bookstore.Mapper.ProductMapper;
import bookstore.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public Object getProductById(Long id) {
        Product product = productRepository.findProductById(id);
        if(product == null) {
            throw new DataNotFoundException(ErrorMessage.Product.PRODUCT_NOT_FOUND,
                    ErrorCode.CODE_ERROR,ErrorObject.PRODUCT);
        }
        ProductDetailDTO dto = new ProductDetailDTO();
        dto = productMapper.toProductDTO(product);
        dto.setCategory(product.getCategory().getName());
        return dto;
    }

    public Object getProductByName(String name) {
        List<Product> products = productRepository.findByName(name);
        List<ProductDetailDTO> productDetailDTOList = convertToProductDetailDTOList(products);

        if (productDetailDTOList.isEmpty()) {
            throw new DataNotFoundException(
                    ErrorMessage.Product.PRODUCT_NOT_FOUND,
                    ErrorCode.CODE_ERROR,
                    ErrorObject.PRODUCT
            );
        }

        return productDetailDTOList;
    }

    public Object getProductByCategory(String category) {
        List<Product> products = productRepository.getProductsByCategory(category);
        return convertToProductDetailDTOList(products);
    }

    public Object getProductMenu() {
        List<Product> products = productRepository.findAll();
        return convertToProductDetailDTOList(products);
    }

    private List<ProductDetailDTO> convertToProductDetailDTOList(List<Product> products) {
        List<ProductDetailDTO> dtoList = new ArrayList<>();
        for (Product p : products) {
            ProductDetailDTO dto = productMapper.toProductDTO(p);
            dto.setCategory(p.getCategory().getName());
            dtoList.add(dto);
        }
        return dtoList;
    }
    public Product getById(Long id) {
        Product product = new Product();
        product =  productRepository.findProductById(id);
        if(product == null) {
            throw new DataNotFoundException(ErrorMessage.Product.PRODUCT_NOT_FOUND, ErrorCode.CODE_ERROR,ErrorObject.PRODUCT);
        }
        return product;
    }
    public void save(Product product) {
        productRepository.save(product);
    }


}
