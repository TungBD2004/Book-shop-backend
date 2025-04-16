package bookstore.Service;

import bookstore.Entity.BillProduct;
import bookstore.Repository.BillProductRepository;
import org.springframework.stereotype.Service;

@Service
public class BillProductService {
    private BillProductRepository billProductRepository;
    public BillProductService(BillProductRepository billProductRepository) {
        this.billProductRepository = billProductRepository;
    }
    public void save(BillProduct billProduct) {
        billProductRepository.save(billProduct);
    }
}
