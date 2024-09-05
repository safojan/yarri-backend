package io.bootify.ngo_app.repos;

import io.bootify.ngo_app.domain.Allocation;
import io.bootify.ngo_app.domain.Donation;
import io.bootify.ngo_app.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AllocationRepository extends JpaRepository<Allocation, Integer> {

    Allocation findFirstByDonation(Donation donation);

    Allocation findFirstByProject(Project project);

}
