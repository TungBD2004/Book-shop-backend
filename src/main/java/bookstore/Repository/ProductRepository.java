package bookstore.Repository;

import bookstore.Entity.Category;
import bookstore.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Product> findByName(@Param("name") String name);

    @Query(value = "select p From Product p where p.category.name = :category ")
    List<Product> getProductsByCategory(@Param("category") String category);

    Product findProductById(long id);

}
