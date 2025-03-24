package bookstore.Controller;

import bookstore.DTO.LoginDTO;
import bookstore.DTO.RegisterDTO;
import bookstore.Exception.Constant.ErrorCode;
import bookstore.Exception.Constant.ErrorMessage;
import bookstore.Exception.Constant.BSResponseEntity;
import bookstore.Service.AuthenticateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/")
public class AuthenticateController {
    private static AuthenticateService authenticateService;
    public AuthenticateController(AuthenticateService authenticateService) {
        this.authenticateService = authenticateService;
    }

    @PostMapping("/login")
    public ResponseEntity<BSResponseEntity> login(@Valid @RequestBody LoginDTO loginDTO) {
        BSResponseEntity ert = new BSResponseEntity();
        ert.setObject(authenticateService.authenticate(loginDTO));
        ert.setCode(ErrorCode.CODE_SUCCESS);
        ert.setMessage(ErrorMessage.User.SUCCESS);
        return ResponseEntity.ok().body(ert);
    }

    @PostMapping("/register")
    public ResponseEntity<BSResponseEntity> register(@Valid @RequestBody RegisterDTO registerDTO) {
        BSResponseEntity ert = new BSResponseEntity();
        ert.setObject(authenticateService.register(registerDTO));
        ert.setCode(ErrorCode.CODE_SUCCESS);
        ert.setMessage(ErrorMessage.User.SUCCESS);
        return ResponseEntity.ok().body(ert);
    }
}
