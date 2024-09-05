package io.bootify.ngo_app.repos;

import io.bootify.ngo_app.domain.Project;
import io.bootify.ngo_app.domain.Task;
import io.bootify.ngo_app.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TaskRepository extends JpaRepository<Task, Integer> {

    Task findFirstByProject(Project project);

    Task findFirstByAssignedTo(User user);

}
