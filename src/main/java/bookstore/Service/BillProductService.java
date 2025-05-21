package bookstore.Service;

import bookstore.DTO.Product.ProductSaleDTO;
import bookstore.Entity.BillProduct;
import bookstore.Repository.BillProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillProductService {
    private BillProductRepository billProductRepository;
    public BillProductService(BillProductRepository billProductRepository) {
        this.billProductRepository = billProductRepository;
    }
    public void save(BillProduct billProduct) {
        billProductRepository.save(billProduct);
    }

    public List<ProductSaleDTO> findSellingProduct(){
        return billProductRepository.findSellingProduct();
    }
}
