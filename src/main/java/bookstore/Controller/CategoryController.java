package bookstore.Controller;

import bookstore.Exception.Constant.BSResponseEntity;
import bookstore.Exception.Constant.ErrorCode;
import bookstore.Exception.Constant.ErrorMessage;
import bookstore.Exception.DataInvalidException;
import bookstore.Exception.DataNotFoundException;
import bookstore.Service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class CategoryController {
    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(value = "/category")
    public ResponseEntity<BSResponseEntity> getCategory() {
        BSResponseEntity ert = new BSResponseEntity();
        try{
            ert.setObject(categoryService.getAllCategories());
            ert.setMessage(ErrorMessage.Common.SUCCESS);
            ert.setCode(ErrorCode.CODE_SUCCESS);
        }
        catch(DataInvalidException e){
            ert.setCode(ErrorCode.CODE_ERROR);
            ert.setMessage(e.getErrMessage());
        }
        catch (DataNotFoundException e){
            ert.setCode(ErrorCode.CODE_ERROR);
            ert.setMessage(e.getErrMessage());
        }
        return ResponseEntity.ok().body(ert);
    }
}
