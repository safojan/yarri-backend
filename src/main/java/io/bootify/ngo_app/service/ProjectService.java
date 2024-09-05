package io.bootify.ngo_app.service;

import io.bootify.ngo_app.domain.Allocation;
import io.bootify.ngo_app.domain.ChildProfile;
import io.bootify.ngo_app.domain.Donation;
import io.bootify.ngo_app.domain.File;
import io.bootify.ngo_app.domain.Milestone;
import io.bootify.ngo_app.domain.Project;
import io.bootify.ngo_app.domain.ProjectAssinee;
import io.bootify.ngo_app.domain.ProjectType;
import io.bootify.ngo_app.domain.RecurringDonation;
import io.bootify.ngo_app.domain.Status;
import io.bootify.ngo_app.domain.Task;
import io.bootify.ngo_app.domain.Transaction;
import io.bootify.ngo_app.domain.User;
import io.bootify.ngo_app.model.ProjectDTO;
import io.bootify.ngo_app.repos.AllocationRepository;
import io.bootify.ngo_app.repos.ChildProfileRepository;
import io.bootify.ngo_app.repos.DonationRepository;
import io.bootify.ngo_app.repos.FileRepository;
import io.bootify.ngo_app.repos.MilestoneRepository;
import io.bootify.ngo_app.repos.ProjectAssineeRepository;
import io.bootify.ngo_app.repos.ProjectRepository;
import io.bootify.ngo_app.repos.ProjectTypeRepository;
import io.bootify.ngo_app.repos.RecurringDonationRepository;
import io.bootify.ngo_app.repos.StatusRepository;
import io.bootify.ngo_app.repos.TaskRepository;
import io.bootify.ngo_app.repos.TransactionRepository;
import io.bootify.ngo_app.repos.UserRepository;
import io.bootify.ngo_app.util.NotFoundException;
import io.bootify.ngo_app.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectTypeRepository projectTypeRepository;
    private final StatusRepository statusRepository;
    private final UserRepository userRepository;
    private final ChildProfileRepository childProfileRepository;
    private final DonationRepository donationRepository;
    private final RecurringDonationRepository recurringDonationRepository;
    private final AllocationRepository allocationRepository;
    private final FileRepository fileRepository;
    private final MilestoneRepository milestoneRepository;
    private final TaskRepository taskRepository;
    private final TransactionRepository transactionRepository;
    private final ProjectAssineeRepository projectAssineeRepository;

    public ProjectService(final ProjectRepository projectRepository,
            final ProjectTypeRepository projectTypeRepository,
            final StatusRepository statusRepository, final UserRepository userRepository,
            final ChildProfileRepository childProfileRepository,
            final DonationRepository donationRepository,
            final RecurringDonationRepository recurringDonationRepository,
            final AllocationRepository allocationRepository, final FileRepository fileRepository,
            final MilestoneRepository milestoneRepository, final TaskRepository taskRepository,
            final TransactionRepository transactionRepository,
            final ProjectAssineeRepository projectAssineeRepository) {
        this.projectRepository = projectRepository;
        this.projectTypeRepository = projectTypeRepository;
        this.statusRepository = statusRepository;
        this.userRepository = userRepository;
        this.childProfileRepository = childProfileRepository;
        this.donationRepository = donationRepository;
        this.recurringDonationRepository = recurringDonationRepository;
        this.allocationRepository = allocationRepository;
        this.fileRepository = fileRepository;
        this.milestoneRepository = milestoneRepository;
        this.taskRepository = taskRepository;
        this.transactionRepository = transactionRepository;
        this.projectAssineeRepository = projectAssineeRepository;
    }

    public List<ProjectDTO> findAll() {
        final List<Project> projects = projectRepository.findAll(Sort.by("id"));
        return projects.stream()
                .map(project -> mapToDTO(project, new ProjectDTO()))
                .toList();
    }

    public ProjectDTO get(final Integer id) {
        return projectRepository.findById(id)
                .map(project -> mapToDTO(project, new ProjectDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final ProjectDTO projectDTO) {
        final Project project = new Project();
        mapToEntity(projectDTO, project);
        return projectRepository.save(project).getId();
    }

    public void update(final Integer id, final ProjectDTO projectDTO) {
        final Project project = projectRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(projectDTO, project);
        projectRepository.save(project);
    }

    public void delete(final Integer id) {
        projectRepository.deleteById(id);
    }

    private ProjectDTO mapToDTO(final Project project, final ProjectDTO projectDTO) {
        projectDTO.setId(project.getId());
        projectDTO.setName(project.getName());
        projectDTO.setDescription(project.getDescription());
        projectDTO.setGoalAmount(project.getGoalAmount());
        projectDTO.setRaisedAmount(project.getRaisedAmount());
        projectDTO.setStartDate(project.getStartDate());
        projectDTO.setEndDate(project.getEndDate());
        projectDTO.setCreatedBy(project.getCreatedBy());
        projectDTO.setType(project.getType() == null ? null : project.getType().getId());
        projectDTO.setStatus(project.getStatus() == null ? null : project.getStatus().getId());
        projectDTO.setUser(project.getUser() == null ? null : project.getUser().getId());
        return projectDTO;
    }

    private Project mapToEntity(final ProjectDTO projectDTO, final Project project) {
        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());
        project.setGoalAmount(projectDTO.getGoalAmount());
        project.setRaisedAmount(projectDTO.getRaisedAmount());
        project.setStartDate(projectDTO.getStartDate());
        project.setEndDate(projectDTO.getEndDate());
        project.setCreatedBy(projectDTO.getCreatedBy());
        final ProjectType type = projectDTO.getType() == null ? null : projectTypeRepository.findById(projectDTO.getType())
                .orElseThrow(() -> new NotFoundException("type not found"));
        project.setType(type);
        final Status status = projectDTO.getStatus() == null ? null : statusRepository.findById(projectDTO.getStatus())
                .orElseThrow(() -> new NotFoundException("status not found"));
        project.setStatus(status);
        final User user = projectDTO.getUser() == null ? null : userRepository.findById(projectDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        project.setUser(user);
        return project;
    }

    public ReferencedWarning getReferencedWarning(final Integer id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Project project = projectRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final ChildProfile projectChildProfile = childProfileRepository.findFirstByProject(project);
        if (projectChildProfile != null) {
            referencedWarning.setKey("project.childProfile.project.referenced");
            referencedWarning.addParam(projectChildProfile.getId());
            return referencedWarning;
        }
        final Donation projectDonation = donationRepository.findFirstByProject(project);
        if (projectDonation != null) {
            referencedWarning.setKey("project.donation.project.referenced");
            referencedWarning.addParam(projectDonation.getId());
            return referencedWarning;
        }
        final RecurringDonation projectRecurringDonation = recurringDonationRepository.findFirstByProject(project);
        if (projectRecurringDonation != null) {
            referencedWarning.setKey("project.recurringDonation.project.referenced");
            referencedWarning.addParam(projectRecurringDonation.getId());
            return referencedWarning;
        }
        final Allocation projectAllocation = allocationRepository.findFirstByProject(project);
        if (projectAllocation != null) {
            referencedWarning.setKey("project.allocation.project.referenced");
            referencedWarning.addParam(projectAllocation.getId());
            return referencedWarning;
        }
        final File projectFile = fileRepository.findFirstByProject(project);
        if (projectFile != null) {
            referencedWarning.setKey("project.file.project.referenced");
            referencedWarning.addParam(projectFile.getId());
            return referencedWarning;
        }
        final Milestone projectMilestone = milestoneRepository.findFirstByProject(project);
        if (projectMilestone != null) {
            referencedWarning.setKey("project.milestone.project.referenced");
            referencedWarning.addParam(projectMilestone.getId());
            return referencedWarning;
        }
        final Task projectTask = taskRepository.findFirstByProject(project);
        if (projectTask != null) {
            referencedWarning.setKey("project.task.project.referenced");
            referencedWarning.addParam(projectTask.getId());
            return referencedWarning;
        }
        final Transaction projectTransaction = transactionRepository.findFirstByProject(project);
        if (projectTransaction != null) {
            referencedWarning.setKey("project.transaction.project.referenced");
            referencedWarning.addParam(projectTransaction.getId());
            return referencedWarning;
        }
        final ProjectAssinee projectProjectAssinee = projectAssineeRepository.findFirstByProject(project);
        if (projectProjectAssinee != null) {
            referencedWarning.setKey("project.projectAssinee.project.referenced");
            referencedWarning.addParam(projectProjectAssinee.getId());
            return referencedWarning;
        }
        return null;
    }

}
