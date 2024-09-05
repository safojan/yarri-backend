package io.bootify.ngo_app.rest;

import io.bootify.ngo_app.model.ProjectTypeDTO;
import io.bootify.ngo_app.service.ProjectTypeService;
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
@RequestMapping(value = "/api/projectTypes", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProjectTypeResource {

    private final ProjectTypeService projectTypeService;

    public ProjectTypeResource(final ProjectTypeService projectTypeService) {
        this.projectTypeService = projectTypeService;
    }

    @GetMapping
    public ResponseEntity<List<ProjectTypeDTO>> getAllProjectTypes() {
        return ResponseEntity.ok(projectTypeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectTypeDTO> getProjectType(
            @PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(projectTypeService.get(id));
    }

    @PostMapping
    public ResponseEntity<Integer> createProjectType(
            @RequestBody @Valid final ProjectTypeDTO projectTypeDTO) {
        final Integer createdId = projectTypeService.create(projectTypeDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateProjectType(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final ProjectTypeDTO projectTypeDTO) {
        projectTypeService.update(id, projectTypeDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProjectType(@PathVariable(name = "id") final Integer id) {
        final ReferencedWarning referencedWarning = projectTypeService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        projectTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
