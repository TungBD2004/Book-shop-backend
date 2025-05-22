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
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')) and p.isDelete = false ")
    List<Product> findByName(@Param("name") String name);

    @Query(value = "select p From Product p where p.category.name = :category and p.isDelete = FALSE ")
    List<Product> getProductsByCategory(@Param("category") String category);

    @Query(value = "select p From Product p where p.id = :id and p.isDelete = FALSE ")
    Product findProductById(@Param("id") Long id);

    @Query(value = "select p From Product p where p.id = :id ")
    Product findProductAllById(@Param("id") Long id);



    @Query(value = "SELECT p FROM Product p where p.isDelete = FALSE ")
    List<Product> findAllProducts();
}
