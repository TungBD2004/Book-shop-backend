package bookstore.Exception;


import bookstore.Exception.Constant.BSResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionTranslator extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = DataNotFoundException.class)
    public org.springframework.http.ResponseEntity<BSResponseEntity> handleDataNotFoundException(DataNotFoundException ex, NativeWebRequest request) {
        return org.springframework.http.ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                BSResponseEntity.builder().message(ex.getErrMessage()).code(Long.valueOf(ex.getCode().toString())).object(ex.getObject()).build()
        );
    }

    @ExceptionHandler(value = DataInvalidException.class)
    public org.springframework.http.ResponseEntity<BSResponseEntity> handleDataInvalidException(DataInvalidException ex, NativeWebRequest request) {
        return org.springframework.http.ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                BSResponseEntity.builder().message(ex.getErrMessage()).code(Long.valueOf(ex.getCode().toString())).object(ex.getObject()).build()
        );
    }

    @ExceptionHandler(value = PermissionDeniedException.class)
    public org.springframework.http.ResponseEntity<BSResponseEntity> handlePermissionDeniedException(PermissionDeniedException ex, NativeWebRequest request) {
        return org.springframework.http.ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                BSResponseEntity.builder().message(ex.getErrMessage()).code(Long.valueOf(ex.getCode().toString())).object(ex.getObject()).build()
        );
    }
    @ExceptionHandler(value = BookShopAuthenticationException.class)
    public org.springframework.http.ResponseEntity<BSResponseEntity> handleBookShopAuthenticationException(BookShopAuthenticationException ex, NativeWebRequest request) {
        return org.springframework.http.ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                BSResponseEntity.builder().message(ex.getErrMessage()).code(Long.valueOf(ex.getCode().toString())).object(ex.getObject()).build()
        );
    }
}

