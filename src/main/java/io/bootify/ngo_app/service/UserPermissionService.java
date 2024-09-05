package io.bootify.ngo_app.service;

import io.bootify.ngo_app.domain.Permission;
import io.bootify.ngo_app.domain.User;
import io.bootify.ngo_app.domain.UserPermission;
import io.bootify.ngo_app.model.UserPermissionDTO;
import io.bootify.ngo_app.repos.PermissionRepository;
import io.bootify.ngo_app.repos.UserPermissionRepository;
import io.bootify.ngo_app.repos.UserRepository;
import io.bootify.ngo_app.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class UserPermissionService {

    private final UserPermissionRepository userPermissionRepository;
    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;

    public UserPermissionService(final UserPermissionRepository userPermissionRepository,
            final UserRepository userRepository, final PermissionRepository permissionRepository) {
        this.userPermissionRepository = userPermissionRepository;
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
    }

    public List<UserPermissionDTO> findAll() {
        final List<UserPermission> userPermissions = userPermissionRepository.findAll(Sort.by("id"));
        return userPermissions.stream()
                .map(userPermission -> mapToDTO(userPermission, new UserPermissionDTO()))
                .toList();
    }

    public UserPermissionDTO get(final Integer id) {
        return userPermissionRepository.findById(id)
                .map(userPermission -> mapToDTO(userPermission, new UserPermissionDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final UserPermissionDTO userPermissionDTO) {
        final UserPermission userPermission = new UserPermission();
        mapToEntity(userPermissionDTO, userPermission);
        return userPermissionRepository.save(userPermission).getId();
    }

    public void update(final Integer id, final UserPermissionDTO userPermissionDTO) {
        final UserPermission userPermission = userPermissionRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userPermissionDTO, userPermission);
        userPermissionRepository.save(userPermission);
    }

    public void delete(final Integer id) {
        userPermissionRepository.deleteById(id);
    }

    private UserPermissionDTO mapToDTO(final UserPermission userPermission,
            final UserPermissionDTO userPermissionDTO) {
        userPermissionDTO.setId(userPermission.getId());
        userPermissionDTO.setUser(userPermission.getUser() == null ? null : userPermission.getUser().getId());
        userPermissionDTO.setPermission(userPermission.getPermission() == null ? null : userPermission.getPermission().getId());
        return userPermissionDTO;
    }

    private UserPermission mapToEntity(final UserPermissionDTO userPermissionDTO,
            final UserPermission userPermission) {
        final User user = userPermissionDTO.getUser() == null ? null : userRepository.findById(userPermissionDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        userPermission.setUser(user);
        final Permission permission = userPermissionDTO.getPermission() == null ? null : permissionRepository.findById(userPermissionDTO.getPermission())
                .orElseThrow(() -> new NotFoundException("permission not found"));
        userPermission.setPermission(permission);
        return userPermission;
    }

}
