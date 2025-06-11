package bookstore.Service;

import bookstore.DTO.MenuDTO;
import bookstore.Entity.RefreshToken;
import bookstore.Entity.Role;
import bookstore.Entity.User;
import bookstore.Entity.UserRole;
import bookstore.Exception.BookShopAuthenticationException;
import bookstore.Exception.Constant.ErrorCode;
import bookstore.Exception.Constant.ErrorMessage;
import bookstore.Exception.Constant.ErrorObject;
import bookstore.Exception.DataNotFoundException;
import bookstore.Mapper.UserMapper;
import bookstore.Repository.RefreshTokenRepository;
import bookstore.Repository.UserRepository;
import bookstore.Request.LoginRequest;
import bookstore.Request.RegisterRequest;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@Service
public class AuthenticateService {
    @Value("${spring.jwt.valid-duration}")
    private long VALID_DURATION;

    @Value("${spring.jwt.signerKey}")
    private String SIGNER_KEY;

    private final UserService userService;
    private final UserMapper userMapper;
    private final UserRoleService userRoleService;
    private final MenuRoleService menuRoleService;
    private final RoleService roleService;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final EmailService emailService;

    @Autowired
    private  StringRedisTemplate redisTemplate;

    public AuthenticateService(UserService userService, UserMapper userMapper, UserRoleService userRoleService,
                               MenuRoleService menuRoleService, RoleService roleService, UserRepository userRepository,
                               RefreshTokenRepository refreshTokenRepository,EmailService emailService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.userRoleService = userRoleService;
        this.menuRoleService = menuRoleService;
        this.roleService = roleService;
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.emailService = emailService;
    }

    public Object authenticate(LoginRequest loginRequest) {
        validateEmail(loginRequest.getEmail());
        User user = userService.findByEmail(loginRequest.getEmail());

        if (user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new BookShopAuthenticationException(
                    ErrorMessage.User.EMAIL_OR_PASSWORD_INCORRECT,
                    ErrorCode.CODE_ERROR,
                    ErrorObject.USER
            );
        }

        return generateTokenResponse(user);
    }

    @Transactional
    public Object register(RegisterRequest request) throws MessagingException, IOException {
        validateEmail(request.getEmail());

        if (userService.findByEmail(request.getEmail()) != null) {
            throw new BookShopAuthenticationException(
                    ErrorMessage.User.USER_ALREADY_EXISTS,
                    ErrorCode.CODE_ERROR,
                    ErrorObject.USER
            );
        }

        if (!request.getPassword().equals(request.getRePassword())) {
            throw new BookShopAuthenticationException(
                    ErrorMessage.User.RE_PASSWORD_NOT_MATCH,
                    ErrorCode.CODE_ERROR,
                    ErrorObject.USER
            );
        }

        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setName(trimString(request.getName()));
        newUser.setAddress(trimString(request.getAddress()));
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setIsActive(false);

        userRepository.save(newUser);

        Role defaultRole = roleService.getRoleById(3L);
        UserRole userRole = new UserRole();
        userRole.setUser(newUser);
        userRole.setRole(defaultRole);
        userRoleService.save(userRole);

        // Tạo token và lưu vào Redis
        String token = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(token, newUser.getId().toString(), 24, TimeUnit.HOURS);
        // Gửi mail xác minh
        emailService.sendHtmlEmail(
                newUser.getEmail(),
                "Xác minh tài khoản",
                "verify_email.html",
                Map.of(
                        "name", newUser.getName(),
                        "link", "http://localhost:8080/api/verify-email?token=" + token
                )
        );

        return request;
    }

    @Transactional
    public Object getTokenByRefreshToken(String token) {
        RefreshToken oldToken = validateRefreshToken(token);
        User user = oldToken.getUser();
        refreshTokenRepository.delete(oldToken);
        return generateTokenResponse(user);
    }

    private void validateEmail(String email) {
        String regex = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (!Pattern.compile(regex).matcher(email).matches()) {
            throw new BookShopAuthenticationException(
                    ErrorMessage.User.EMAIL_INVALID,
                    ErrorCode.CODE_ERROR,
                    ErrorObject.USER
            );
        }
    }

    private String trimString(String value) {
        return Optional.ofNullable(value).map(v -> v.trim().replaceAll("\\s+", " ")).orElse("");
    }

    private RefreshToken validateRefreshToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token);
        if (refreshToken == null || refreshToken.getExpiredDate().before(Date.from(Instant.now()))) {
            if (refreshToken != null) refreshTokenRepository.delete(refreshToken);
            throw new DataNotFoundException("Refresh token is invalid or expired",
                    ErrorCode.CODE_ERROR, ErrorObject.USER);
        }
        return refreshToken;
    }

    private Object generateTokenResponse(User user) {
        String token = createJwtToken(user);
        String refreshTokenStr = UUID.randomUUID().toString();

        RefreshToken newRefreshToken = new RefreshToken();
        newRefreshToken.setUser(user);
        newRefreshToken.setRefreshToken(refreshTokenStr);
        newRefreshToken.setExpiredDate(Date.from(Instant.now().plus(VALID_DURATION * 10, ChronoUnit.MINUTES)));
        refreshTokenRepository.save(newRefreshToken);

        List<Role> roles = roleService.getRoleByUserId(user.getId());
        List<String> roleNames = userRoleService.getNameRoleByUserId(user.getId());
        Set<MenuDTO> menus = menuRoleService.getMenuDTO(roles, 26L);

        return Map.of(
                "idToken", token,
                "refreshToken", refreshTokenStr,
                "user", userMapper.UserToUserDTO(user),
                "roles", roleNames,
                "menuDTOS", menus
        );
    }

    private String createJwtToken(User user) {
        try {
            JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
            Role role = roleService.getHighestRole(user);

            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .subject(user.getEmail())
                    .issuer("bookstore")
                    .issueTime(new Date())
                    .expirationTime(Date.from(Instant.now().plus(7, ChronoUnit.DAYS)))
                    .claim("scope", role.getName())
                    .build();

            JWSObject jws = new JWSObject(header, new Payload(claims.toJSONObject()));
            jws.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jws.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException("Unable to create JWT", e);
        }
    }


    public Object verifyEmailToken(String token) {
        String userIdStr = redisTemplate.opsForValue().get(token);

        if (userIdStr == null) {
            return ResponseEntity.badRequest()
                    .body("Token không hợp lệ hoặc đã hết hạn.");
        }

        Long userId = Long.parseLong(userIdStr);
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("Người dùng không tồn tại.");
        }

        User user = optionalUser.get();
        user.setIsActive(true);
        userRepository.save(user);
        redisTemplate.delete(token);

        return null;
    }
}

