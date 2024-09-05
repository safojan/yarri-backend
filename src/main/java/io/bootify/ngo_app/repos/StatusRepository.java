package io.bootify.ngo_app.repos;

import io.bootify.ngo_app.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StatusRepository extends JpaRepository<Status, Integer> {
}
