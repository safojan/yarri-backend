package io.bootify.ngo_app.repos;

import io.bootify.ngo_app.domain.Milestone;
import io.bootify.ngo_app.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MilestoneRepository extends JpaRepository<Milestone, Integer> {

    Milestone findFirstByProject(Project project);

}
