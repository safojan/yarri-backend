package io.bootify.ngo_app.repos;

import io.bootify.ngo_app.domain.ChildProfile;
import io.bootify.ngo_app.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ChildProfileRepository extends JpaRepository<ChildProfile, Integer> {

    ChildProfile findFirstByProject(Project project);

}
