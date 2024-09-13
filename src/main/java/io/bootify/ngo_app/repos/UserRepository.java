package io.bootify.ngo_app.repos;

import io.bootify.ngo_app.domain.Role;
import io.bootify.ngo_app.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {

    User findFirstByRole(Role role);
    User findByEmail(String email);



}
