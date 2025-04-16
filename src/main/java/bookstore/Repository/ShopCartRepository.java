package bookstore.Repository;

import bookstore.Entity.ShopCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopCartRepository extends JpaRepository<ShopCart, Long> {
    @Query(value = "select sc from ShopCart sc where sc.user.id = :userId and sc.product.id = :productId")
    ShopCart findByUserIdAndProductId(@Param("userId")   Long userId,
                                      @Param("productId")  Long productId);

    List<ShopCart> findByUserId(Long userId);



}
