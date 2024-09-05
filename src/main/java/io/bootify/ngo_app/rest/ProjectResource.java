package io.bootify.ngo_app.rest;

import io.bootify.ngo_app.model.ProjectDTO;
import io.bootify.ngo_app.service.ProjectService;
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
@RequestMapping(value = "/api/projects", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProjectResource {

    private final ProjectService projectService;

    public ProjectResource(final ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public ResponseEntity<List<ProjectDTO>> getAllProjects() {
        return ResponseEntity.ok(projectService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getProject(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(projectService.get(id));
    }

    @PostMapping
    public ResponseEntity<Integer> createProject(@RequestBody @Valid final ProjectDTO projectDTO) {
        final Integer createdId = projectService.create(projectDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateProject(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final ProjectDTO projectDTO) {
        projectService.update(id, projectDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable(name = "id") final Integer id) {
        final ReferencedWarning referencedWarning = projectService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        projectService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
