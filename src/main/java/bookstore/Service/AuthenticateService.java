package bookstore.Service;

import bookstore.DTO.MenuDTO;
import bookstore.Request.RegisterRequest;
import bookstore.Entity.Role;
import bookstore.Entity.User;
import bookstore.Entity.UserRole;
import bookstore.Exception.BookShopAuthenticationException;
import bookstore.Exception.Constant.ErrorCode;
import bookstore.Exception.Constant.ErrorMessage;
import bookstore.Exception.Constant.ErrorObject;
import bookstore.Mapper.UserMapper;
import bookstore.Repository.UserRepository;
import bookstore.Request.LoginRequest;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

@Service
public class AuthenticateService {
    protected static final String SIGNER_KEY = "4FeXWK+GkmP9QBx92WgUsIrrJ738N7Z/t+5YL4XKnqpGGlV5u+gP+xp5NIezvy8l";
    @Value("${spring.jwt.valid-duration}")
    private long VALID_DURATION;

    private final UserService userService;
    private final UserMapper userMapper;
    private final UserRoleService userRoleService;
    private final MenuRoleService menuRoleService;
    private final RoleService roleService;
    private final UserRepository userRepository;

    public AuthenticateService(UserService userService, UserMapper userMapper, UserRoleService userRoleService,
                               MenuRoleService menuRoleService, RoleService roleService, UserRepository userRepository
    ) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.userRoleService = userRoleService;
        this.menuRoleService = menuRoleService;
        this.roleService = roleService;
        this.userRepository = userRepository;
    }
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
    public Object authenticate(LoginRequest loginRequest) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!isValidEmail(loginRequest.getEmail())) {
            throw new BookShopAuthenticationException(
                    ErrorMessage.User.EMAIL_INVALID,
                    ErrorCode.CODE_ERROR,
                    ErrorObject.USER
            );
        }
        User user = userService.findByEmail(loginRequest.getEmail());

        if (user == null) {
            throw new BookShopAuthenticationException(
                    ErrorMessage.User.USER_NOT_EXISTS,
                    ErrorCode.CODE_ERROR,
                    ErrorObject.USER
            );
        }
        boolean authenticated = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());
        if (!authenticated) {
            throw new BookShopAuthenticationException(
                    ErrorMessage.User.EMAIL_OR_PASSWORD_INCORRECT,
                    ErrorCode.CODE_ERROR,
                    ErrorObject.USER
            );
        }

        List<Role> roles = roleService.getRoleByUserId(user.getId());
        List<String> roleName = userRoleService.getNameRoleByUserId(user.getId());
        Set<MenuDTO> menuDTOS = menuRoleService.getMenuDTO(roles, 26L);

        // Tạo token và trả về response
        var token = createToken(user);
        return Map.of(
                "idToken", token,
                "user", userMapper.UserToUserDTO(user),
                "roles", roleName,
                "menuDTOS", menuDTOS
        );
    }


    public Object register(RegisterRequest registerRequest) {
        if (!isValidEmail(registerRequest.getEmail())) {
            throw new BookShopAuthenticationException(
                    ErrorMessage.User.EMAIL_INVALID,
                    ErrorCode.CODE_ERROR,
                    ErrorObject.USER
            );
        }

        User user = userService.findByEmail(registerRequest.getEmail());
        if (user != null) {
            throw new BookShopAuthenticationException(
                    ErrorMessage.User.USER_ALREADY_EXISTS,
                    ErrorCode.CODE_ERROR,
                    ErrorObject.USER
            );
        }

        if (!registerRequest.getPassword().equals(registerRequest.getRePassword())) {
            throw new BookShopAuthenticationException(
                    ErrorMessage.User.RE_PASSWORD_NOT_MATCH,
                    ErrorCode.CODE_ERROR,
                    ErrorObject.USER
            );
        }
        User newUser = new User();
        newUser.setEmail(registerRequest.getEmail());
        newUser.setName(registerRequest.getName().trim().replaceAll("\\s+", " "));
        newUser.setAddress(registerRequest.getAddress().trim().replaceAll("\\s+", " "));

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
        newUser.setPassword(encodedPassword);

        userRepository.save(newUser);

        Role roleUser = roleService.getRoleById(3L);
        UserRole userRole = new UserRole();
        userRole.setUser(newUser);
        userRole.setRole(roleUser);
        userRoleService.save(userRole);

        return registerRequest;
    }

    public String createToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        Role role = roleService.getHighestRole(user);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .issuer("")
                .issueTime(new Date())
                .expirationTime(Date.from(Instant.now().plus(7, ChronoUnit.DAYS)))
                .claim("scope", role.getName())
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            return "Can Not Create Token";
        }
    }


}
