package io.bootify.ngo_app.repos;

import io.bootify.ngo_app.domain.Project;
import io.bootify.ngo_app.domain.ProjectType;
import io.bootify.ngo_app.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProjectRepository extends JpaRepository<Project, Integer> {

    Project findFirstByType(ProjectType projectType);

    Project findFirstByUser(User user);

}
