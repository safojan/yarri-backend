package io.bootify.ngo_app.repos;

import io.bootify.ngo_app.domain.Permission;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PermissionRepository extends JpaRepository<Permission, Integer> {
}
