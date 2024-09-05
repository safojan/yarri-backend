package io.bootify.ngo_app.service;

import io.bootify.ngo_app.domain.Project;
import io.bootify.ngo_app.domain.Status;
import io.bootify.ngo_app.domain.Task;
import io.bootify.ngo_app.domain.User;
import io.bootify.ngo_app.model.TaskDTO;
import io.bootify.ngo_app.repos.ProjectRepository;
import io.bootify.ngo_app.repos.StatusRepository;
import io.bootify.ngo_app.repos.TaskRepository;
import io.bootify.ngo_app.repos.UserRepository;
import io.bootify.ngo_app.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final StatusRepository statusRepository;
    private final UserRepository userRepository;

    public TaskService(final TaskRepository taskRepository,
            final ProjectRepository projectRepository, final StatusRepository statusRepository,
            final UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.statusRepository = statusRepository;
        this.userRepository = userRepository;
    }

    public List<TaskDTO> findAll() {
        final List<Task> tasks = taskRepository.findAll(Sort.by("id"));
        return tasks.stream()
                .map(task -> mapToDTO(task, new TaskDTO()))
                .toList();
    }

    public TaskDTO get(final Integer id) {
        return taskRepository.findById(id)
                .map(task -> mapToDTO(task, new TaskDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final TaskDTO taskDTO) {
        final Task task = new Task();
        mapToEntity(taskDTO, task);
        return taskRepository.save(task).getId();
    }

    public void update(final Integer id, final TaskDTO taskDTO) {
        final Task task = taskRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(taskDTO, task);
        taskRepository.save(task);
    }

    public void delete(final Integer id) {
        taskRepository.deleteById(id);
    }

    private TaskDTO mapToDTO(final Task task, final TaskDTO taskDTO) {
        taskDTO.setId(task.getId());
        taskDTO.setName(task.getName());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setStartDate(task.getStartDate());
        taskDTO.setEndDate(task.getEndDate());
        taskDTO.setProject(task.getProject() == null ? null : task.getProject().getId());
        taskDTO.setStatus(task.getStatus() == null ? null : task.getStatus().getId());
        taskDTO.setAssignedTo(task.getAssignedTo() == null ? null : task.getAssignedTo().getId());
        return taskDTO;
    }

    private Task mapToEntity(final TaskDTO taskDTO, final Task task) {
        task.setName(taskDTO.getName());
        task.setDescription(taskDTO.getDescription());
        task.setStartDate(taskDTO.getStartDate());
        task.setEndDate(taskDTO.getEndDate());
        final Project project = taskDTO.getProject() == null ? null : projectRepository.findById(taskDTO.getProject())
                .orElseThrow(() -> new NotFoundException("project not found"));
        task.setProject(project);
        final Status status = taskDTO.getStatus() == null ? null : statusRepository.findById(taskDTO.getStatus())
                .orElseThrow(() -> new NotFoundException("status not found"));
        task.setStatus(status);
        final User assignedTo = taskDTO.getAssignedTo() == null ? null : userRepository.findById(taskDTO.getAssignedTo())
                .orElseThrow(() -> new NotFoundException("assignedTo not found"));
        task.setAssignedTo(assignedTo);
        return task;
    }

}
