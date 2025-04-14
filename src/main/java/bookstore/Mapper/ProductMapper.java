package bookstore.Mapper;

import bookstore.DTO.Product.ProductDetailDTO;
import bookstore.Entity.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {

    public ProductDetailDTO toProductDTO(Product product) {
        ProductDetailDTO productDetailDTO = new ProductDetailDTO();
        productDetailDTO.setId(product.getId());
        productDetailDTO.setName(product.getName());
        productDetailDTO.setDescription(product.getDescription());
        productDetailDTO.setPrice(product.getPrice());
        productDetailDTO.setQuantity(product.getQuantity());
        productDetailDTO.setAuthor(product.getAuthor());
        productDetailDTO.setImageUrl(product.getImageUrl());
        return productDetailDTO;
    }
}
