package bookstore.Repository;

import bookstore.Entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
     @Query("""
        select rt
        from RefreshToken rt
        where rt.refreshToken = :token
    """)
    RefreshToken findByToken(String token);
}
