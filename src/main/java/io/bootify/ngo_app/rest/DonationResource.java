package io.bootify.ngo_app.rest;

import io.bootify.ngo_app.model.DonationDTO;
import io.bootify.ngo_app.service.DonationService;
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
@RequestMapping(value = "/api/donations", produces = MediaType.APPLICATION_JSON_VALUE)
public class DonationResource {

    private final DonationService donationService;

    public DonationResource(final DonationService donationService) {
        this.donationService = donationService;
    }

    @GetMapping
    public ResponseEntity<List<DonationDTO>> getAllDonations() {
        return ResponseEntity.ok(donationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonationDTO> getDonation(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(donationService.get(id));
    }

    @PostMapping
    public ResponseEntity<Integer> createDonation(
            @RequestBody @Valid final DonationDTO donationDTO) {
        final Integer createdId = donationService.create(donationDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateDonation(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final DonationDTO donationDTO) {
        donationService.update(id, donationDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDonation(@PathVariable(name = "id") final Integer id) {
        final ReferencedWarning referencedWarning = donationService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        donationService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
