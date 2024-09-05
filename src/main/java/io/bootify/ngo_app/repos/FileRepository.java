package io.bootify.ngo_app.repos;

import io.bootify.ngo_app.domain.File;
import io.bootify.ngo_app.domain.Project;
import io.bootify.ngo_app.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FileRepository extends JpaRepository<File, Integer> {

    File findFirstByUser(User user);

    File findFirstByProject(Project project);

}
