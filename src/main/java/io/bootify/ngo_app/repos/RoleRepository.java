package io.bootify.ngo_app.repos;

import io.bootify.ngo_app.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, Integer> {
 //role by role name
    Role findByName(String name);
}
