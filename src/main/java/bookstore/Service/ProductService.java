package bookstore.Service;

import bookstore.DTO.Product.ProductDTO;
import bookstore.DTO.Product.ProductDetailDTO;
import bookstore.Entity.Product;
import bookstore.Exception.BookShopAuthenticationException;
import bookstore.Exception.Constant.ErrorCode;
import bookstore.Exception.Constant.ErrorMessage;
import bookstore.Exception.Constant.ErrorObject;
import bookstore.Exception.DataNotFoundException;
import bookstore.Mapper.ProductMapper;
import bookstore.Repository.ProductRepository;
import bookstore.Request.ProductRequest.ProductDetailRequest;
import bookstore.Util.BSUtil;
import org.antlr.v4.runtime.misc.ObjectEqualityComparator;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryService categoryService;
    private final BSUtil bsUtil;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper, CategoryService categoryService, BSUtil bsUtil) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.categoryService = categoryService;
        this.bsUtil = bsUtil;
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
        String nameBook = name.trim().replaceAll("\\s+", " ");
        List<Product> products = productRepository.findByName(nameBook);
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
        List<Product> products = productRepository.findAllProducts();
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

    private void mapProductDetail(ProductDetailRequest productDTO, Product product) throws IOException {
        product.setName(productDTO.getName().trim().replaceAll("\\s+", " "));
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());
        product.setAuthor(productDTO.getAuthor().trim().replaceAll("\\s+", " "));
        if(productDTO.getImageUrl() != null) {
            String imageUrl = bsUtil.uploadImageBase64(productDTO.getImageUrl());
            product.setImageUrl(imageUrl);
        }

        product.setCategory(categoryService.getByName(productDTO.getCategory()));
    }
    public Object addProduct(ProductDetailRequest productDTO) {
        Product product = new Product();
        try {
            mapProductDetail(productDTO, product);
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi upload ảnh lên Cloudinary", e);
        }
        productRepository.save(product);
        return productMapper.toProductDTO(product);
    }

    public Object updateProduct(ProductDetailRequest productDTO) {
        Product product = productRepository.findById(productDTO.getId())
                .orElseThrow(() -> new DataNotFoundException(
                        ErrorMessage.Product.PRODUCT_NOT_FOUND,
                        ErrorCode.CODE_ERROR,
                        ErrorObject.PRODUCT
                ));

        try {
            mapProductDetail(productDTO, product);
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi upload ảnh lên Cloudinary", e);
        }

        productRepository.save(product);
        return productMapper.toProductDTO(product);
    }


    public Object deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() ->
                new DataNotFoundException(ErrorMessage.Product.PRODUCT_NOT_FOUND, ErrorCode.CODE_ERROR,ErrorObject.PRODUCT));
        product.setIsDelete(true);
        productRepository.save(product);
        return null;
    }

}
