package io.bootify.ngo_app.repos;

import io.bootify.ngo_app.domain.Project;
import io.bootify.ngo_app.domain.RecurringDonation;
import io.bootify.ngo_app.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RecurringDonationRepository extends JpaRepository<RecurringDonation, Integer> {

    RecurringDonation findFirstByUser(User user);

    RecurringDonation findFirstByProject(Project project);

}
