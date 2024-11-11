package io.bootify.ngo_app.repos;

import io.bootify.ngo_app.domain.Permission;
import io.bootify.ngo_app.domain.User;
import io.bootify.ngo_app.domain.UserPermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserPermissionRepository extends JpaRepository<UserPermission, Integer> {

    UserPermission findFirstByUser(User user);

    UserPermission findFirstByPermission(Permission permission);

    List<UserPermission> findByUserId(Integer id);
}
