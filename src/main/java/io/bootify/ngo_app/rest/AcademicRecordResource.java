package io.bootify.ngo_app.rest;

import io.bootify.ngo_app.model.AcademicRecordDTO;
import io.bootify.ngo_app.service.AcademicRecordService;
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
@RequestMapping(value = "/api/academicRecords", produces = MediaType.APPLICATION_JSON_VALUE)
public class AcademicRecordResource {

    private final AcademicRecordService academicRecordService;

    public AcademicRecordResource(final AcademicRecordService academicRecordService) {
        this.academicRecordService = academicRecordService;
    }

    @GetMapping
    public ResponseEntity<List<AcademicRecordDTO>> getAllAcademicRecords() {
        return ResponseEntity.ok(academicRecordService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AcademicRecordDTO> getAcademicRecord(
            @PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(academicRecordService.get(id));
    }

    @PostMapping
    public ResponseEntity<Integer> createAcademicRecord(
            @RequestBody @Valid final AcademicRecordDTO academicRecordDTO) {
        final Integer createdId = academicRecordService.create(academicRecordDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateAcademicRecord(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final AcademicRecordDTO academicRecordDTO) {
        academicRecordService.update(id, academicRecordDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAcademicRecord(@PathVariable(name = "id") final Integer id) {
        academicRecordService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
