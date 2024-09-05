package io.bootify.ngo_app.rest;

import io.bootify.ngo_app.model.ProjectAssineeDTO;
import io.bootify.ngo_app.service.ProjectAssineeService;
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
@RequestMapping(value = "/api/projectAssinees", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProjectAssineeResource {

    private final ProjectAssineeService projectAssineeService;

    public ProjectAssineeResource(final ProjectAssineeService projectAssineeService) {
        this.projectAssineeService = projectAssineeService;
    }

    @GetMapping
    public ResponseEntity<List<ProjectAssineeDTO>> getAllProjectAssinees() {
        return ResponseEntity.ok(projectAssineeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectAssineeDTO> getProjectAssinee(
            @PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(projectAssineeService.get(id));
    }

    @PostMapping
    public ResponseEntity<Integer> createProjectAssinee(
            @RequestBody @Valid final ProjectAssineeDTO projectAssineeDTO) {
        final Integer createdId = projectAssineeService.create(projectAssineeDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateProjectAssinee(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final ProjectAssineeDTO projectAssineeDTO) {
        projectAssineeService.update(id, projectAssineeDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProjectAssinee(@PathVariable(name = "id") final Integer id) {
        projectAssineeService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
