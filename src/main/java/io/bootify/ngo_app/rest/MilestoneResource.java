package io.bootify.ngo_app.rest;

import io.bootify.ngo_app.model.MilestoneDTO;
import io.bootify.ngo_app.service.MilestoneService;
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
@RequestMapping(value = "/api/milestones", produces = MediaType.APPLICATION_JSON_VALUE)
public class MilestoneResource {

    private final MilestoneService milestoneService;

    public MilestoneResource(final MilestoneService milestoneService) {
        this.milestoneService = milestoneService;
    }

    @GetMapping
    public ResponseEntity<List<MilestoneDTO>> getAllMilestones() {
        return ResponseEntity.ok(milestoneService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MilestoneDTO> getMilestone(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(milestoneService.get(id));
    }

    @PostMapping
    public ResponseEntity<Integer> createMilestone(
            @RequestBody @Valid final MilestoneDTO milestoneDTO) {
        final Integer createdId = milestoneService.create(milestoneDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateMilestone(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final MilestoneDTO milestoneDTO) {
        milestoneService.update(id, milestoneDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMilestone(@PathVariable(name = "id") final Integer id) {
        milestoneService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
