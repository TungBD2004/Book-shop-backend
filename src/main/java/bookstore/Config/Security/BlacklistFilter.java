package bookstore.Config.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class BlacklistFilter extends OncePerRequestFilter {

    private final RedisTemplate<String, String> redisTemplate;

    public BlacklistFilter(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();

        // Bỏ qua kiểm tra danh sách đen cho /api/menu/homepage
        if ("/api/menu/homepage".equals(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            // Kiểm tra token trong danh sách đen
            String redisKey = "blacklist:token:" + token;
            if (Boolean.TRUE.equals(redisTemplate.hasKey(redisKey))) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Trả về mã 401, không có body
                return;
            }
        }

        // Cho phép yêu cầu tiếp tục
        filterChain.doFilter(request, response);
    }
}