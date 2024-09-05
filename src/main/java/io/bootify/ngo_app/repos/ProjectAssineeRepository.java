package io.bootify.ngo_app.repos;

import io.bootify.ngo_app.domain.Project;
import io.bootify.ngo_app.domain.ProjectAssinee;
import io.bootify.ngo_app.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProjectAssineeRepository extends JpaRepository<ProjectAssinee, Integer> {

    ProjectAssinee findFirstByProject(Project project);

    ProjectAssinee findFirstByUser(User user);

}
