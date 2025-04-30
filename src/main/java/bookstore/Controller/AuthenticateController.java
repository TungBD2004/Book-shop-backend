package bookstore.Controller;


import bookstore.DTO.RegisterRequest;
import bookstore.Exception.BookShopAuthenticationException;
import bookstore.Exception.Constant.BSResponseEntity;
import bookstore.Exception.Constant.ErrorCode;
import bookstore.Exception.Constant.ErrorMessage;
import bookstore.Request.LoginRequest;
import bookstore.Service.AuthenticateService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            ert.setMessage(ErrorMessage.Common.SUCCESS);
        } catch (BookShopAuthenticationException e) {
            ert.setCode(ErrorCode.CODE_ERROR);
            ert.setMessage(e.getMessage());
        }
        return ResponseEntity.ok().body(ert);
    }


}
