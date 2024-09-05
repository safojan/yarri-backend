package io.bootify.ngo_app.service;

import io.bootify.ngo_app.domain.Donation;
import io.bootify.ngo_app.domain.File;
import io.bootify.ngo_app.domain.Notification;
import io.bootify.ngo_app.domain.Project;
import io.bootify.ngo_app.domain.ProjectAssinee;
import io.bootify.ngo_app.domain.RecurringDonation;
import io.bootify.ngo_app.domain.Role;
import io.bootify.ngo_app.domain.Task;
import io.bootify.ngo_app.domain.User;
import io.bootify.ngo_app.domain.UserPermission;
import io.bootify.ngo_app.model.UserDTO;
import io.bootify.ngo_app.repos.DonationRepository;
import io.bootify.ngo_app.repos.FileRepository;
import io.bootify.ngo_app.repos.NotificationRepository;
import io.bootify.ngo_app.repos.ProjectAssineeRepository;
import io.bootify.ngo_app.repos.ProjectRepository;
import io.bootify.ngo_app.repos.RecurringDonationRepository;
import io.bootify.ngo_app.repos.RoleRepository;
import io.bootify.ngo_app.repos.TaskRepository;
import io.bootify.ngo_app.repos.UserPermissionRepository;
import io.bootify.ngo_app.repos.UserRepository;
import io.bootify.ngo_app.util.NotFoundException;
import io.bootify.ngo_app.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserPermissionRepository userPermissionRepository;
    private final ProjectRepository projectRepository;
    private final DonationRepository donationRepository;
    private final RecurringDonationRepository recurringDonationRepository;
    private final NotificationRepository notificationRepository;
    private final FileRepository fileRepository;
    private final TaskRepository taskRepository;
    private final ProjectAssineeRepository projectAssineeRepository;

    public UserService(final UserRepository userRepository, final RoleRepository roleRepository,
            final UserPermissionRepository userPermissionRepository,
            final ProjectRepository projectRepository, final DonationRepository donationRepository,
            final RecurringDonationRepository recurringDonationRepository,
            final NotificationRepository notificationRepository,
            final FileRepository fileRepository, final TaskRepository taskRepository,
            final ProjectAssineeRepository projectAssineeRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userPermissionRepository = userPermissionRepository;
        this.projectRepository = projectRepository;
        this.donationRepository = donationRepository;
        this.recurringDonationRepository = recurringDonationRepository;
        this.notificationRepository = notificationRepository;
        this.fileRepository = fileRepository;
        this.taskRepository = taskRepository;
        this.projectAssineeRepository = projectAssineeRepository;
    }

    public List<UserDTO> findAll() {
        final List<User> users = userRepository.findAll(Sort.by("id"));
        return users.stream()
                .map(user -> mapToDTO(user, new UserDTO()))
                .toList();
    }

    public UserDTO get(final Integer id) {
        return userRepository.findById(id)
                .map(user -> mapToDTO(user, new UserDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final UserDTO userDTO) {
        final User user = new User();
        mapToEntity(userDTO, user);
        return userRepository.save(user).getId();
    }

    public void update(final Integer id, final UserDTO userDTO) {
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userDTO, user);
        userRepository.save(user);
    }

    public void delete(final Integer id) {
        userRepository.deleteById(id);
    }

    private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setPhone(user.getPhone());
        userDTO.setAddress(user.getAddress());
        userDTO.setRole(user.getRole() == null ? null : user.getRole().getId());
        return userDTO;
    }

    private User mapToEntity(final UserDTO userDTO, final User user) {
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setPhone(userDTO.getPhone());
        user.setAddress(userDTO.getAddress());
        final Role role = userDTO.getRole() == null ? null : roleRepository.findById(userDTO.getRole())
                .orElseThrow(() -> new NotFoundException("role not found"));
        user.setRole(role);
        return user;
    }

    public ReferencedWarning getReferencedWarning(final Integer id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final UserPermission userUserPermission = userPermissionRepository.findFirstByUser(user);
        if (userUserPermission != null) {
            referencedWarning.setKey("user.userPermission.user.referenced");
            referencedWarning.addParam(userUserPermission.getId());
            return referencedWarning;
        }
        final Project userProject = projectRepository.findFirstByUser(user);
        if (userProject != null) {
            referencedWarning.setKey("user.project.user.referenced");
            referencedWarning.addParam(userProject.getId());
            return referencedWarning;
        }
        final Donation userDonation = donationRepository.findFirstByUser(user);
        if (userDonation != null) {
            referencedWarning.setKey("user.donation.user.referenced");
            referencedWarning.addParam(userDonation.getId());
            return referencedWarning;
        }
        final RecurringDonation userRecurringDonation = recurringDonationRepository.findFirstByUser(user);
        if (userRecurringDonation != null) {
            referencedWarning.setKey("user.recurringDonation.user.referenced");
            referencedWarning.addParam(userRecurringDonation.getId());
            return referencedWarning;
        }
        final Notification userNotification = notificationRepository.findFirstByUser(user);
        if (userNotification != null) {
            referencedWarning.setKey("user.notification.user.referenced");
            referencedWarning.addParam(userNotification.getId());
            return referencedWarning;
        }
        final File userFile = fileRepository.findFirstByUser(user);
        if (userFile != null) {
            referencedWarning.setKey("user.file.user.referenced");
            referencedWarning.addParam(userFile.getId());
            return referencedWarning;
        }
        final Task assignedToTask = taskRepository.findFirstByAssignedTo(user);
        if (assignedToTask != null) {
            referencedWarning.setKey("user.task.assignedTo.referenced");
            referencedWarning.addParam(assignedToTask.getId());
            return referencedWarning;
        }
        final ProjectAssinee userProjectAssinee = projectAssineeRepository.findFirstByUser(user);
        if (userProjectAssinee != null) {
            referencedWarning.setKey("user.projectAssinee.user.referenced");
            referencedWarning.addParam(userProjectAssinee.getId());
            return referencedWarning;
        }
        return null;
    }

}
