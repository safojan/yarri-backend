package io.bootify.ngo_app.rest;


import com.fasterxml.jackson.annotation.JsonView;
import io.bootify.ngo_app.model.CustomDTO.PermissionsDTO;
import io.bootify.ngo_app.model.CustomDTO.UserDetailsDTO;
import io.bootify.ngo_app.model.RoleDTO;
import io.bootify.ngo_app.model.UserDTO;
import io.bootify.ngo_app.model.UserPermissionDTO;
import io.bootify.ngo_app.service.CustomUserDetailsService;
import io.bootify.ngo_app.service.UserService;
import io.bootify.ngo_app.util.ReferencedException;
import io.bootify.ngo_app.util.ReferencedWarning;
import io.bootify.ngo_app.views.UserViews;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserResource {

    private final UserService userService;
  private final CustomUserDetailsService customUserDetailsService;

  public UserResource(final UserService userService, CustomUserDetailsService customUserDetailsService) {
        this.userService = userService;
    this.customUserDetailsService = customUserDetailsService;
  }

    @GetMapping
    @JsonView(UserViews.WithoutPassword.class)
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    //get users by email in the body of the request


    @GetMapping("/{id}")
    @JsonView(UserViews.WithoutPassword.class)
    public ResponseEntity<UserDTO> getUser(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(userService.get(id));
    }


  @GetMapping("/current/details")
  @JsonView(UserViews.WithoutPassword.class)
  public ResponseEntity<UserDTO> getCurrentUserDetails() {
    UserDTO currentUser=userService.getCurrentUser();
    return ResponseEntity.ok(currentUser);
  }


  @GetMapping("/current/permissions")
  public ResponseEntity<List<PermissionsDTO>> getCurrentUserPermissions() {
    List<PermissionsDTO> currentUserPermissions=userService.getCurrentUserPermissions();
    return ResponseEntity.ok(currentUserPermissions);
  }

  @GetMapping("/current/role")
  public ResponseEntity<RoleDTO> getCurrentUserRole() {
           //complete with error handling
    RoleDTO CurrentUserRole=userService.getCurrentUserRole();
    return ResponseEntity.ok(CurrentUserRole);
  }


    @PostMapping
    public ResponseEntity<Integer> createUser(@RequestBody @Valid final UserDTO userDTO) {
        final Integer createdId = userService.create(userDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateUser(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final UserDTO userDTO) {
        userService.update(id, userDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable(name = "id") final Integer id) {
        final ReferencedWarning referencedWarning = userService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }



}
