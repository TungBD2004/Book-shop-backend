package bookstore.Repository;

import bookstore.Entity.BillProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillProductRepository extends JpaRepository<BillProduct, Long> {
}
