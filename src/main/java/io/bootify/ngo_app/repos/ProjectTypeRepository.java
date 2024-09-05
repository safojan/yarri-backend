package io.bootify.ngo_app.repos;

import io.bootify.ngo_app.domain.ProjectType;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProjectTypeRepository extends JpaRepository<ProjectType, Integer> {
}
