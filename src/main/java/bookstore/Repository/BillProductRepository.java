package bookstore.Repository;

import bookstore.DTO.Product.ProductSaleDTO;
import bookstore.Entity.BillProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillProductRepository extends JpaRepository<BillProduct, Long> {

    @Query("SELECT bp.product.id AS productId, SUM(bp.quantity) AS totalSold " +
            "FROM BillProduct bp where bp.product.isDelete = false" +
            " group by bp.product.id " +
            "ORDER BY totalSold DESC")
    List<ProductSaleDTO> findSellingProduct();
}
