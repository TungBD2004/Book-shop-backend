package bookstore.Config.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class OptionalJwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtDecoder jwtDecoder;
    private final JwtAuthenticationConverter jwtAuthenticationConverter;
    private final RedisTemplate<String, String> redisTemplate;

    public OptionalJwtAuthenticationFilter(JwtDecoder jwtDecoder, JwtAuthenticationConverter jwtAuthenticationConverter,
                                           RedisTemplate<String, String> redisTemplate) {
        this.jwtDecoder = jwtDecoder;
        this.jwtAuthenticationConverter = jwtAuthenticationConverter;
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();

        if ("/api/menu/homepage".equals(path)) {
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                try {
                    String token = authHeader.substring(7);

                    // Xác thực token (BlacklistFilter đã kiểm tra danh sách đen)
                    JwtAuthenticationToken authentication = authenticateToken(token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } catch (JwtException ex) {
                    // Token sai hoặc không hợp lệ, bỏ qua và cho đi tiếp
                    SecurityContextHolder.clearContext();
                }
            } else {
                // Không có token, cho đi tiếp
                SecurityContextHolder.clearContext();
            }
            filterChain.doFilter(request, response);
        } else {
            // Các endpoint khác, để Spring Security xử lý bình thường
            filterChain.doFilter(request, response);
        }
    }

    private JwtAuthenticationToken authenticateToken(String token) {
        var jwt = jwtDecoder.decode(token);
        return (JwtAuthenticationToken) jwtAuthenticationConverter.convert(jwt);
    }
}