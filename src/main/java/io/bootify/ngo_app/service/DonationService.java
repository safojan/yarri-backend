package io.bootify.ngo_app.service;

import io.bootify.ngo_app.domain.Allocation;
import io.bootify.ngo_app.domain.Donation;
import io.bootify.ngo_app.domain.Payment;
import io.bootify.ngo_app.domain.Project;
import io.bootify.ngo_app.domain.Status;
import io.bootify.ngo_app.domain.User;
import io.bootify.ngo_app.model.DonationDTO;
import io.bootify.ngo_app.repos.AllocationRepository;
import io.bootify.ngo_app.repos.DonationRepository;
import io.bootify.ngo_app.repos.PaymentRepository;
import io.bootify.ngo_app.repos.ProjectRepository;
import io.bootify.ngo_app.repos.StatusRepository;
import io.bootify.ngo_app.repos.UserRepository;
import io.bootify.ngo_app.util.NotFoundException;
import io.bootify.ngo_app.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class DonationService {

    private final DonationRepository donationRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final StatusRepository statusRepository;
    private final AllocationRepository allocationRepository;
    private final PaymentRepository paymentRepository;

    public DonationService(final DonationRepository donationRepository,
            final UserRepository userRepository, final ProjectRepository projectRepository,
            final StatusRepository statusRepository,
            final AllocationRepository allocationRepository,
            final PaymentRepository paymentRepository) {
        this.donationRepository = donationRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.statusRepository = statusRepository;
        this.allocationRepository = allocationRepository;
        this.paymentRepository = paymentRepository;
    }

    public List<DonationDTO> findAll() {
        final List<Donation> donations = donationRepository.findAll(Sort.by("id"));
        return donations.stream()
                .map(donation -> mapToDTO(donation, new DonationDTO()))
                .toList();
    }

    public DonationDTO get(final Integer id) {
        return donationRepository.findById(id)
                .map(donation -> mapToDTO(donation, new DonationDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final DonationDTO donationDTO) {
        final Donation donation = new Donation();
        mapToEntity(donationDTO, donation);
        return donationRepository.save(donation).getId();
    }

    public void update(final Integer id, final DonationDTO donationDTO) {
        final Donation donation = donationRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(donationDTO, donation);
        donationRepository.save(donation);
    }

    public void delete(final Integer id) {
        donationRepository.deleteById(id);
    }

    private DonationDTO mapToDTO(final Donation donation, final DonationDTO donationDTO) {
        donationDTO.setId(donation.getId());
        donationDTO.setAmount(donation.getAmount());
        donationDTO.setCurrency(donation.getCurrency());
        donationDTO.setUser(donation.getUser() == null ? null : donation.getUser().getId());
        donationDTO.setProject(donation.getProject() == null ? null : donation.getProject().getId());
        donationDTO.setStatus(donation.getStatus() == null ? null : donation.getStatus().getId());
        return donationDTO;
    }

    private Donation mapToEntity(final DonationDTO donationDTO, final Donation donation) {
        donation.setAmount(donationDTO.getAmount());
        donation.setCurrency(donationDTO.getCurrency());
        final User user = donationDTO.getUser() == null ? null : userRepository.findById(donationDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        donation.setUser(user);
        final Project project = donationDTO.getProject() == null ? null : projectRepository.findById(donationDTO.getProject())
                .orElseThrow(() -> new NotFoundException("project not found"));
        donation.setProject(project);
        final Status status = donationDTO.getStatus() == null ? null : statusRepository.findById(donationDTO.getStatus())
                .orElseThrow(() -> new NotFoundException("status not found"));
        donation.setStatus(status);
        return donation;
    }

    public ReferencedWarning getReferencedWarning(final Integer id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Donation donation = donationRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Allocation donationAllocation = allocationRepository.findFirstByDonation(donation);
        if (donationAllocation != null) {
            referencedWarning.setKey("donation.allocation.donation.referenced");
            referencedWarning.addParam(donationAllocation.getId());
            return referencedWarning;
        }
        final Payment donationPayment = paymentRepository.findFirstByDonation(donation);
        if (donationPayment != null) {
            referencedWarning.setKey("donation.payment.donation.referenced");
            referencedWarning.addParam(donationPayment.getId());
            return referencedWarning;
        }
        return null;
    }

}
