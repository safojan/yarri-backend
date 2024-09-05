package io.bootify.ngo_app.service;

import io.bootify.ngo_app.domain.Project;
import io.bootify.ngo_app.domain.ProjectType;
import io.bootify.ngo_app.model.ProjectTypeDTO;
import io.bootify.ngo_app.repos.ProjectRepository;
import io.bootify.ngo_app.repos.ProjectTypeRepository;
import io.bootify.ngo_app.util.NotFoundException;
import io.bootify.ngo_app.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ProjectTypeService {

    private final ProjectTypeRepository projectTypeRepository;
    private final ProjectRepository projectRepository;

    public ProjectTypeService(final ProjectTypeRepository projectTypeRepository,
            final ProjectRepository projectRepository) {
        this.projectTypeRepository = projectTypeRepository;
        this.projectRepository = projectRepository;
    }

    public List<ProjectTypeDTO> findAll() {
        final List<ProjectType> projectTypes = projectTypeRepository.findAll(Sort.by("id"));
        return projectTypes.stream()
                .map(projectType -> mapToDTO(projectType, new ProjectTypeDTO()))
                .toList();
    }

    public ProjectTypeDTO get(final Integer id) {
        return projectTypeRepository.findById(id)
                .map(projectType -> mapToDTO(projectType, new ProjectTypeDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final ProjectTypeDTO projectTypeDTO) {
        final ProjectType projectType = new ProjectType();
        mapToEntity(projectTypeDTO, projectType);
        return projectTypeRepository.save(projectType).getId();
    }

    public void update(final Integer id, final ProjectTypeDTO projectTypeDTO) {
        final ProjectType projectType = projectTypeRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(projectTypeDTO, projectType);
        projectTypeRepository.save(projectType);
    }

    public void delete(final Integer id) {
        projectTypeRepository.deleteById(id);
    }

    private ProjectTypeDTO mapToDTO(final ProjectType projectType,
            final ProjectTypeDTO projectTypeDTO) {
        projectTypeDTO.setId(projectType.getId());
        projectTypeDTO.setName(projectType.getName());
        return projectTypeDTO;
    }

    private ProjectType mapToEntity(final ProjectTypeDTO projectTypeDTO,
            final ProjectType projectType) {
        projectType.setName(projectTypeDTO.getName());
        return projectType;
    }

    public ReferencedWarning getReferencedWarning(final Integer id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final ProjectType projectType = projectTypeRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Project typeProject = projectRepository.findFirstByType(projectType);
        if (typeProject != null) {
            referencedWarning.setKey("projectType.project.type.referenced");
            referencedWarning.addParam(typeProject.getId());
            return referencedWarning;
        }
        return null;
    }

}
