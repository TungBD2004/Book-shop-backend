package bookstore.Repository;

import bookstore.Entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    @Query(value = "select b from Bill b where b.user.id = :userId")
    List<Bill>  findBillByUserId(@Param("userId") Long userId);

}
