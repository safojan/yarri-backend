package io.bootify.ngo_app.service;

import io.bootify.ngo_app.domain.Milestone;
import io.bootify.ngo_app.domain.Project;
import io.bootify.ngo_app.domain.Status;
import io.bootify.ngo_app.model.MilestoneDTO;
import io.bootify.ngo_app.repos.MilestoneRepository;
import io.bootify.ngo_app.repos.ProjectRepository;
import io.bootify.ngo_app.repos.StatusRepository;
import io.bootify.ngo_app.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class MilestoneService {

    private final MilestoneRepository milestoneRepository;
    private final ProjectRepository projectRepository;
    private final StatusRepository statusRepository;

    public MilestoneService(final MilestoneRepository milestoneRepository,
            final ProjectRepository projectRepository, final StatusRepository statusRepository) {
        this.milestoneRepository = milestoneRepository;
        this.projectRepository = projectRepository;
        this.statusRepository = statusRepository;
    }

    public List<MilestoneDTO> findAll() {
        final List<Milestone> milestones = milestoneRepository.findAll(Sort.by("id"));
        return milestones.stream()
                .map(milestone -> mapToDTO(milestone, new MilestoneDTO()))
                .toList();
    }

    public MilestoneDTO get(final Integer id) {
        return milestoneRepository.findById(id)
                .map(milestone -> mapToDTO(milestone, new MilestoneDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final MilestoneDTO milestoneDTO) {
        final Milestone milestone = new Milestone();
        mapToEntity(milestoneDTO, milestone);
        return milestoneRepository.save(milestone).getId();
    }

    public void update(final Integer id, final MilestoneDTO milestoneDTO) {
        final Milestone milestone = milestoneRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(milestoneDTO, milestone);
        milestoneRepository.save(milestone);
    }

    public void delete(final Integer id) {
        milestoneRepository.deleteById(id);
    }

    private MilestoneDTO mapToDTO(final Milestone milestone, final MilestoneDTO milestoneDTO) {
        milestoneDTO.setId(milestone.getId());
        milestoneDTO.setName(milestone.getName());
        milestoneDTO.setDescription(milestone.getDescription());
        milestoneDTO.setDueDate(milestone.getDueDate());
        milestoneDTO.setProject(milestone.getProject() == null ? null : milestone.getProject().getId());
        milestoneDTO.setStatus(milestone.getStatus() == null ? null : milestone.getStatus().getId());
        return milestoneDTO;
    }

    private Milestone mapToEntity(final MilestoneDTO milestoneDTO, final Milestone milestone) {
        milestone.setName(milestoneDTO.getName());
        milestone.setDescription(milestoneDTO.getDescription());
        milestone.setDueDate(milestoneDTO.getDueDate());
        final Project project = milestoneDTO.getProject() == null ? null : projectRepository.findById(milestoneDTO.getProject())
                .orElseThrow(() -> new NotFoundException("project not found"));
        milestone.setProject(project);
        final Status status = milestoneDTO.getStatus() == null ? null : statusRepository.findById(milestoneDTO.getStatus())
                .orElseThrow(() -> new NotFoundException("status not found"));
        milestone.setStatus(status);
        return milestone;
    }

}
