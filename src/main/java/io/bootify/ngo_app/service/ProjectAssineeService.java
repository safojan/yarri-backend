package io.bootify.ngo_app.service;

import io.bootify.ngo_app.domain.Project;
import io.bootify.ngo_app.domain.ProjectAssinee;
import io.bootify.ngo_app.domain.User;
import io.bootify.ngo_app.model.ProjectAssineeDTO;
import io.bootify.ngo_app.repos.ProjectAssineeRepository;
import io.bootify.ngo_app.repos.ProjectRepository;
import io.bootify.ngo_app.repos.UserRepository;
import io.bootify.ngo_app.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ProjectAssineeService {

    private final ProjectAssineeRepository projectAssineeRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectAssineeService(final ProjectAssineeRepository projectAssineeRepository,
            final ProjectRepository projectRepository, final UserRepository userRepository) {
        this.projectAssineeRepository = projectAssineeRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public List<ProjectAssineeDTO> findAll() {
        final List<ProjectAssinee> projectAssinees = projectAssineeRepository.findAll(Sort.by("id"));
        return projectAssinees.stream()
                .map(projectAssinee -> mapToDTO(projectAssinee, new ProjectAssineeDTO()))
                .toList();
    }

    public ProjectAssineeDTO get(final Integer id) {
        return projectAssineeRepository.findById(id)
                .map(projectAssinee -> mapToDTO(projectAssinee, new ProjectAssineeDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final ProjectAssineeDTO projectAssineeDTO) {
        final ProjectAssinee projectAssinee = new ProjectAssinee();
        mapToEntity(projectAssineeDTO, projectAssinee);
        return projectAssineeRepository.save(projectAssinee).getId();
    }

    public void update(final Integer id, final ProjectAssineeDTO projectAssineeDTO) {
        final ProjectAssinee projectAssinee = projectAssineeRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(projectAssineeDTO, projectAssinee);
        projectAssineeRepository.save(projectAssinee);
    }

    public void delete(final Integer id) {
        projectAssineeRepository.deleteById(id);
    }

    private ProjectAssineeDTO mapToDTO(final ProjectAssinee projectAssinee,
            final ProjectAssineeDTO projectAssineeDTO) {
        projectAssineeDTO.setId(projectAssinee.getId());
        projectAssineeDTO.setProject(projectAssinee.getProject() == null ? null : projectAssinee.getProject().getId());
        projectAssineeDTO.setUser(projectAssinee.getUser() == null ? null : projectAssinee.getUser().getId());
        return projectAssineeDTO;
    }

    private ProjectAssinee mapToEntity(final ProjectAssineeDTO projectAssineeDTO,
            final ProjectAssinee projectAssinee) {
        final Project project = projectAssineeDTO.getProject() == null ? null : projectRepository.findById(projectAssineeDTO.getProject())
                .orElseThrow(() -> new NotFoundException("project not found"));
        projectAssinee.setProject(project);
        final User user = projectAssineeDTO.getUser() == null ? null : userRepository.findById(projectAssineeDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        projectAssinee.setUser(user);
        return projectAssinee;
    }

}
