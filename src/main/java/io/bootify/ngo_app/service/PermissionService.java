package io.bootify.ngo_app.service;

import io.bootify.ngo_app.domain.Permission;
import io.bootify.ngo_app.domain.UserPermission;
import io.bootify.ngo_app.model.PermissionDTO;
import io.bootify.ngo_app.repos.PermissionRepository;
import io.bootify.ngo_app.repos.UserPermissionRepository;
import io.bootify.ngo_app.util.NotFoundException;
import io.bootify.ngo_app.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PermissionService {

    private final PermissionRepository permissionRepository;
    private final UserPermissionRepository userPermissionRepository;

    public PermissionService(final PermissionRepository permissionRepository,
            final UserPermissionRepository userPermissionRepository) {
        this.permissionRepository = permissionRepository;
        this.userPermissionRepository = userPermissionRepository;
    }

    public List<PermissionDTO> findAll() {
        final List<Permission> permissions = permissionRepository.findAll(Sort.by("id"));
        return permissions.stream()
                .map(permission -> mapToDTO(permission, new PermissionDTO()))
                .toList();
    }

    public PermissionDTO get(final Integer id) {
        return permissionRepository.findById(id)
                .map(permission -> mapToDTO(permission, new PermissionDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final PermissionDTO permissionDTO) {
        final Permission permission = new Permission();
        mapToEntity(permissionDTO, permission);
        return permissionRepository.save(permission).getId();
    }

    public void update(final Integer id, final PermissionDTO permissionDTO) {
        final Permission permission = permissionRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(permissionDTO, permission);
        permissionRepository.save(permission);
    }

    public void delete(final Integer id) {
        permissionRepository.deleteById(id);
    }

    private PermissionDTO mapToDTO(final Permission permission, final PermissionDTO permissionDTO) {
        permissionDTO.setId(permission.getId());
        permissionDTO.setName(permission.getName());
        permissionDTO.setDescription(permission.getDescription());
        return permissionDTO;
    }

    private Permission mapToEntity(final PermissionDTO permissionDTO, final Permission permission) {
        permission.setName(permissionDTO.getName());
        permission.setDescription(permissionDTO.getDescription());
        return permission;
    }

    public ReferencedWarning getReferencedWarning(final Integer id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Permission permission = permissionRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final UserPermission permissionUserPermission = userPermissionRepository.findFirstByPermission(permission);
        if (permissionUserPermission != null) {
            referencedWarning.setKey("permission.userPermission.permission.referenced");
            referencedWarning.addParam(permissionUserPermission.getId());
            return referencedWarning;
        }
        return null;
    }

}
