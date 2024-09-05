package io.bootify.ngo_app.repos;

import io.bootify.ngo_app.domain.Donation;
import io.bootify.ngo_app.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    Payment findFirstByDonation(Donation donation);

}
