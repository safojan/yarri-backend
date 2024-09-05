package io.bootify.ngo_app.rest;

import io.bootify.ngo_app.model.UserPermissionDTO;
import io.bootify.ngo_app.service.UserPermissionService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/userPermissions", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserPermissionResource {

    private final UserPermissionService userPermissionService;

    public UserPermissionResource(final UserPermissionService userPermissionService) {
        this.userPermissionService = userPermissionService;
    }

    @GetMapping
    public ResponseEntity<List<UserPermissionDTO>> getAllUserPermissions() {
        return ResponseEntity.ok(userPermissionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserPermissionDTO> getUserPermission(
            @PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(userPermissionService.get(id));
    }

    @PostMapping
    public ResponseEntity<Integer> createUserPermission(
            @RequestBody @Valid final UserPermissionDTO userPermissionDTO) {
        final Integer createdId = userPermissionService.create(userPermissionDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateUserPermission(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final UserPermissionDTO userPermissionDTO) {
        userPermissionService.update(id, userPermissionDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserPermission(@PathVariable(name = "id") final Integer id) {
        userPermissionService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
