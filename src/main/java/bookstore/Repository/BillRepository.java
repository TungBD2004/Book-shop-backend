package bookstore.Repository;

import bookstore.Entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    @Query(value = "select b from Bill b where b.user.id = :userId")
    List<Bill>  findBillByUserId(@Param("userId") Long userId);

    @Query("SELECT b FROM Bill b " +
            "WHERE (:email IS NULL OR :email = '' OR b.user.email LIKE CONCAT('%', :email, '%')) " +
            "AND (:fromDate IS NULL OR b.date >= :fromDate) " +
            "AND (:toDate IS NULL OR b.date <= :toDate) " +
            "AND (:status IS NULL OR :status = '' OR b.status = :status)")
    List<Bill> findBySearchType(@Param("email") String email,
                                @Param("fromDate") Date fromDate,
                                @Param("toDate") Date toDate,
                                @Param("status") String status);


}
