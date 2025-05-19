package bookstore.Controller;

import bookstore.Exception.Constant.BSResponseEntity;
import bookstore.Exception.Constant.ErrorCode;
import bookstore.Exception.Constant.ErrorMessage;
import bookstore.Exception.DataInvalidException;
import bookstore.Exception.DataNotFoundException;
import bookstore.Request.UserRequest.ChangePasswordRequest;
import bookstore.Request.UserRequest.UpdateUserRequest;
import bookstore.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/")
public class UserController {
    private final UserService userService;
    public UserController( UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/admin/user")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN')")
    public ResponseEntity<BSResponseEntity> getAllUser() {
        BSResponseEntity ert = new BSResponseEntity();
        try{
            ert.setObject(userService.getAllUser());
            ert.setMessage(ErrorMessage.Common.SUCCESS);
            ert.setCode(ErrorCode.CODE_SUCCESS);
        }
        catch(Exception e){
            ert.setMessage(ErrorMessage.Common.WRONG);
            ert.setCode(ErrorCode.CODE_ERROR);
        }
        return ResponseEntity.ok().body(ert);
    }

    @PutMapping("admin/user/{id}")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN')")
    public ResponseEntity<BSResponseEntity> updateUserByAdmin(@PathVariable Long id, @RequestBody UpdateUserRequest updateUserRequest) {
        BSResponseEntity ert = new BSResponseEntity();
        try{
            ert.setObject(userService.updateUserByAdmin(id, updateUserRequest));
            ert.setMessage(ErrorMessage.User.UPDATE_USER_SUCCESS);
            ert.setCode(ErrorCode.CODE_SUCCESS);
        } catch (DataNotFoundException e) {
            ert.setMessage(e.getErrMessage());
            ert.setCode(ErrorCode.CODE_ERROR);
        }
        catch (Exception e){
            ert.setMessage(e.getMessage());
            ert.setCode(ErrorCode.CODE_ERROR);
        }
        return ResponseEntity.ok().body(ert);
    }

    @PutMapping("/user")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN','USER')")
    public ResponseEntity<BSResponseEntity> updateUserByOwner (@Valid @RequestBody UpdateUserRequest updateUserRequest) {
        BSResponseEntity ert = new BSResponseEntity();
        try{
            ert.setObject(userService.updateUserByOwner(updateUserRequest));
            ert.setMessage(ErrorMessage.User.UPDATE_USER_SUCCESS);
            ert.setCode(ErrorCode.CODE_SUCCESS);
        } catch (DataNotFoundException e) {
            ert.setMessage(e.getErrMessage());
            ert.setCode(ErrorCode.CODE_ERROR);
        }
        catch (Exception e){
            ert.setMessage(e.getMessage());
            ert.setCode(ErrorCode.CODE_ERROR);
        }
        return ResponseEntity.ok().body(ert);
    }


    @DeleteMapping("/admin/user/{id}")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN')")
    public ResponseEntity<BSResponseEntity> deleteUser(@PathVariable Long id) {
        BSResponseEntity ert = new BSResponseEntity();
        try{
            ert.setObject(userService.deleteUser(id));
            ert.setMessage(ErrorMessage.User.DELETE_USER_SUCCESS);
            ert.setCode(ErrorCode.CODE_SUCCESS);
        } catch (DataNotFoundException e) {
            ert.setMessage(e.getErrMessage());
            ert.setCode(ErrorCode.CODE_ERROR);
        }
        return ResponseEntity.ok().body(ert);
    }

    @PutMapping("/user/change-password")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN','USER')")
    public ResponseEntity<BSResponseEntity> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        BSResponseEntity ert = new BSResponseEntity();
        try{
            ert.setObject(userService.changePassword(changePasswordRequest));
            ert.setMessage(ErrorMessage.User.CHANGE_PASSWORD_SUCCESS);
            ert.setCode(ErrorCode.CODE_SUCCESS);
        } catch (DataNotFoundException e) {
            ert.setMessage(e.getErrMessage());
            ert.setCode(ErrorCode.CODE_ERROR);
        }
        catch (DataInvalidException e) {
            ert.setMessage(e.getErrMessage());
            ert.setCode(ErrorCode.CODE_ERROR);
        }
        catch (Exception e){
            ert.setMessage(e.getMessage());
            ert.setCode(ErrorCode.CODE_ERROR);
        }
        return ResponseEntity.ok().body(ert);
    }

    @GetMapping("/user/info")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN','USER')")
    public ResponseEntity<BSResponseEntity> getUserInfo() {
        BSResponseEntity ert = new BSResponseEntity();
        try{
            ert.setObject(userService.getInfoByOwner());
            ert.setMessage(ErrorMessage.Common.SUCCESS);
            ert.setCode(ErrorCode.CODE_SUCCESS);
        } catch (DataNotFoundException e) {
            ert.setMessage(e.getErrMessage());
            ert.setCode(ErrorCode.CODE_ERROR);
        }
        catch (DataInvalidException e) {
            ert.setMessage(e.getErrMessage());
            ert.setCode(ErrorCode.CODE_ERROR);
        }
        catch (Exception e){
            ert.setMessage(e.getMessage());
            ert.setCode(ErrorCode.CODE_ERROR);
        }
        return ResponseEntity.ok().body(ert);
    }

    @PutMapping("/admin/user/reset-password/{id}")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN')")
    public ResponseEntity<BSResponseEntity> resetPassword(@PathVariable Long id) {
        BSResponseEntity ert = new BSResponseEntity();
        try{
            ert.setObject(userService.resetPassword(id));
            ert.setMessage(ErrorMessage.User.RESET_PASSWORD_SUCCESS);
            ert.setCode(ErrorCode.CODE_SUCCESS);
        } catch (DataNotFoundException e) {
            ert.setMessage(e.getErrMessage());
            ert.setCode(ErrorCode.CODE_ERROR);
        } catch (DataInvalidException e) {
            ert.setMessage(e.getErrMessage());
            ert.setCode(ErrorCode.CODE_ERROR);
        }
        catch (Exception e){
            ert.setMessage(e.getMessage());
            ert.setCode(ErrorCode.CODE_ERROR);
        }
        return ResponseEntity.ok().body(ert);
    }

    @PutMapping("/admin/user/add-role-admin/{id}")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN')")
    public ResponseEntity<BSResponseEntity> addRoleAdmin(@PathVariable Long id) {
        BSResponseEntity ert = new BSResponseEntity();
        try{
            ert.setObject(userService.addRoleAdmin(id));
            ert.setMessage(ErrorMessage.User.ADD_ROLE_ADMIN_SUCCESS);
            ert.setCode(ErrorCode.CODE_SUCCESS);
        } catch (DataNotFoundException e) {
            ert.setMessage(e.getErrMessage());
            ert.setCode(ErrorCode.CODE_ERROR);
        } catch (DataInvalidException e) {
            ert.setMessage(e.getErrMessage());
            ert.setCode(ErrorCode.CODE_ERROR);
        }
        catch (Exception e){
            ert.setMessage(e.getMessage());
            ert.setCode(ErrorCode.CODE_ERROR);
        }
        return ResponseEntity.ok().body(ert);
    }

    @PutMapping("/admin/user/remove-role-admin/{id}")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN')")
    public ResponseEntity<BSResponseEntity> removeRoleAdmin(@PathVariable Long id) {
        BSResponseEntity ert = new BSResponseEntity();
        try{
            ert.setObject(userService.removeRoleAdmin(id));
            ert.setMessage(ErrorMessage.User.REMOVE_ROLE_ADMIN_SUCCESS);
            ert.setCode(ErrorCode.CODE_SUCCESS);
        } catch (DataNotFoundException e) {
            ert.setMessage(e.getErrMessage());
            ert.setCode(ErrorCode.CODE_ERROR);
        } catch (DataInvalidException e) {
            ert.setMessage(e.getErrMessage());
            ert.setCode(ErrorCode.CODE_ERROR);
        }
        catch (Exception e){
            ert.setMessage(e.getMessage());
            ert.setCode(ErrorCode.CODE_ERROR);
        }
        return ResponseEntity.ok().body(ert);
    }

    @GetMapping("/admin/user/{email}")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN')")
    public ResponseEntity<BSResponseEntity> findByEmail(@PathVariable String email) {
        BSResponseEntity ert = new BSResponseEntity();
        try{
            ert.setObject(userService.findUsersByEmail(email));
            ert.setMessage(ErrorMessage.Common.SUCCESS);
            ert.setCode(ErrorCode.CODE_SUCCESS);
        } catch (DataNotFoundException e) {
            ert.setMessage(e.getErrMessage());
            ert.setCode(ErrorCode.CODE_ERROR);
        } catch (DataInvalidException e) {
            ert.setMessage(e.getErrMessage());
            ert.setCode(ErrorCode.CODE_ERROR);
        }
        catch (Exception e){
            ert.setMessage(e.getMessage());
            ert.setCode(ErrorCode.CODE_ERROR);
        }
        return ResponseEntity.ok().body(ert);
    }

    @GetMapping("/admin/user/{id}")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN')")
    public ResponseEntity<BSResponseEntity> findByEmail(@PathVariable Long id) {
        BSResponseEntity ert = new BSResponseEntity();
        try{
            ert.setObject(userService.getInfoByOwnerById(id));
            ert.setMessage(ErrorMessage.Common.SUCCESS);
            ert.setCode(ErrorCode.CODE_SUCCESS);
        } catch (DataNotFoundException e) {
            ert.setMessage(e.getErrMessage());
            ert.setCode(ErrorCode.CODE_ERROR);
        } catch (DataInvalidException e) {
            ert.setMessage(e.getErrMessage());
            ert.setCode(ErrorCode.CODE_ERROR);
        }
        catch (Exception e){
            ert.setMessage(e.getMessage());
            ert.setCode(ErrorCode.CODE_ERROR);
        }
        return ResponseEntity.ok().body(ert);
    }


}
