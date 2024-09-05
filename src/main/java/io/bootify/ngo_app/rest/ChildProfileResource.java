package io.bootify.ngo_app.rest;

import io.bootify.ngo_app.model.ChildProfileDTO;
import io.bootify.ngo_app.service.ChildProfileService;
import io.bootify.ngo_app.util.ReferencedException;
import io.bootify.ngo_app.util.ReferencedWarning;
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
@RequestMapping(value = "/api/childProfiles", produces = MediaType.APPLICATION_JSON_VALUE)
public class ChildProfileResource {

    private final ChildProfileService childProfileService;

    public ChildProfileResource(final ChildProfileService childProfileService) {
        this.childProfileService = childProfileService;
    }

    @GetMapping
    public ResponseEntity<List<ChildProfileDTO>> getAllChildProfiles() {
        return ResponseEntity.ok(childProfileService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChildProfileDTO> getChildProfile(
            @PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(childProfileService.get(id));
    }

    @PostMapping
    public ResponseEntity<Integer> createChildProfile(
            @RequestBody @Valid final ChildProfileDTO childProfileDTO) {
        final Integer createdId = childProfileService.create(childProfileDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateChildProfile(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final ChildProfileDTO childProfileDTO) {
        childProfileService.update(id, childProfileDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChildProfile(@PathVariable(name = "id") final Integer id) {
        final ReferencedWarning referencedWarning = childProfileService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        childProfileService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
