package bookstore.Controller;

import bookstore.DTO.LoginDTO;
import bookstore.DTO.RegisterDTO;
import bookstore.Exception.BookShopAuthenticationException;
import bookstore.Exception.Constant.ErrorCode;
import bookstore.Exception.Constant.ErrorMessage;
import bookstore.Exception.Constant.BSResponseEntity;
import bookstore.Service.AuthenticateService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/")
@Tag(name = "Authentication API", description = "API for user authentication")
public class AuthenticateController {
    private final AuthenticateService authenticateService;
    public AuthenticateController(AuthenticateService authenticateService) {
        this.authenticateService = authenticateService;
    }

    @PostMapping("/login")
    public ResponseEntity<BSResponseEntity> login(@Valid @RequestBody LoginDTO loginDTO) {
        BSResponseEntity ert = new BSResponseEntity();
        try {
            ert.setObject(authenticateService.authenticate(loginDTO));
            ert.setCode(ErrorCode.CODE_SUCCESS);
            ert.setMessage("Đăng nhập thành công");
        } catch (BookShopAuthenticationException e) {
            ert.setCode(ErrorCode.CODE_ERROR);
            ert.setMessage(e.getErrMessage());
        }
        return ResponseEntity.ok().body(ert);
    }

    @PostMapping("/register")
    public ResponseEntity<BSResponseEntity> register(@Valid @RequestBody RegisterDTO registerDTO) {
        BSResponseEntity ert = new BSResponseEntity();
        try {
            ert.setObject(authenticateService.register(registerDTO));
            ert.setCode(ErrorCode.CODE_SUCCESS);
            ert.setMessage(ErrorMessage.User.SUCCESS);
        } catch (BookShopAuthenticationException e) {
            ert.setCode(ErrorCode.CODE_ERROR);
            ert.setMessage(e.getMessage());
        }
        return ResponseEntity.ok().body(ert);
    }


}
