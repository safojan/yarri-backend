package io.bootify.ngo_app.repos;

import io.bootify.ngo_app.domain.Donation;
import io.bootify.ngo_app.domain.Project;
import io.bootify.ngo_app.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DonationRepository extends JpaRepository<Donation, Integer> {

    Donation findFirstByUser(User user);

    Donation findFirstByProject(Project project);

}
