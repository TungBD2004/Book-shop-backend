package bookstore.Controller;

import bookstore.Exception.Constant.BSResponseEntity;
import bookstore.Exception.Constant.ErrorCode;
import bookstore.Exception.DataInvalidException;
import bookstore.Exception.DataNotFoundException;
import bookstore.Service.MenuRoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class MenuController {
    private MenuRoleService menuRoleService;
    public MenuController(MenuRoleService menuRoleService) {
        this.menuRoleService = menuRoleService;
    }

    @GetMapping("/menu/homepage")
    public ResponseEntity<BSResponseEntity> getMenuHomepage(){
        BSResponseEntity ert = new BSResponseEntity();
        try{
            ert.setObject(menuRoleService.getMenuHomepage());
            ert.setCode(ErrorCode.CODE_SUCCESS);
            ert.setMessage("Home Page");
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
