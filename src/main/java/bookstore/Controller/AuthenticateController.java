package bookstore.Controller;


import bookstore.Exception.BookShopAuthenticationException;
import bookstore.Exception.Constant.BSResponseEntity;
import bookstore.Exception.Constant.ErrorCode;
import bookstore.Exception.Constant.ErrorMessage;
import bookstore.Request.LoginRequest;
import bookstore.Request.RefreshTokenRequest;
import bookstore.Request.RegisterRequest;
import bookstore.Service.AuthenticateService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/")
@Tag(name = "Authentication API", description = "API for user authentication")
public class AuthenticateController {
    private final AuthenticateService authenticateService;
    public AuthenticateController(AuthenticateService authenticateService) {
        this.authenticateService = authenticateService;
    }

    @PostMapping("/login")
    public ResponseEntity<BSResponseEntity> login(@Valid @RequestBody LoginRequest loginRequest) {
        BSResponseEntity ert = new BSResponseEntity();
        try {
            ert.setObject(authenticateService.authenticate(loginRequest));
            ert.setCode(ErrorCode.CODE_SUCCESS);
            ert.setMessage(ErrorMessage.Common.LOGIN_SUCCESS);
        } catch (BookShopAuthenticationException e) {
            ert.setCode(ErrorCode.CODE_ERROR);
            ert.setMessage(e.getErrMessage());
        } catch (Exception e) {
            ert.setCode(ErrorCode.CODE_ERROR);
            ert.setMessage(e.getMessage());
        }
        return ResponseEntity.ok().body(ert);
    }

    @PostMapping("/register")
    public ResponseEntity<BSResponseEntity> register(@Valid @RequestBody RegisterRequest registerRequest) {
        BSResponseEntity ert = new BSResponseEntity();
        try {
            ert.setObject(authenticateService.register(registerRequest));
            ert.setCode(ErrorCode.CODE_SUCCESS);
            ert.setMessage(ErrorMessage.Common.REGISTER_SUCCESS);
        } catch (BookShopAuthenticationException e) {
            ert.setCode(ErrorCode.CODE_ERROR);
            ert.setMessage(e.getErrMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok().body(ert);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<BSResponseEntity> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshToken) {
        BSResponseEntity ert = new BSResponseEntity();
        try {
            ert.setObject(authenticateService.getTokenByRefreshToken(refreshToken.getRefreshToken()));
            ert.setCode(ErrorCode.CODE_SUCCESS);
            ert.setMessage(ErrorMessage.Common.SUCCESS);
        } catch (BookShopAuthenticationException e) {
            ert.setCode(ErrorCode.CODE_ERROR);
            ert.setMessage(e.getErrMessage());
        }
        return ResponseEntity.ok().body(ert);
    }

    @GetMapping("/verify-email")
    public ResponseEntity<BSResponseEntity> verifyEmail(@RequestParam String token) {
        BSResponseEntity ert = new BSResponseEntity();
        try{
            ert.setObject(authenticateService.verifyEmailToken(token));
            ert.setCode(ErrorCode.CODE_SUCCESS);
            ert.setMessage(ErrorMessage.Common.SUCCESS);
        }
        catch (BookShopAuthenticationException e){
            ert.setCode(ErrorCode.CODE_ERROR);
            ert.setMessage(e.getErrMessage());
        }
        return ResponseEntity.ok().body(ert);
    }

}
