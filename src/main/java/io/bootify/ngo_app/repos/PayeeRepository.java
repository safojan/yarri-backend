package io.bootify.ngo_app.repos;

import io.bootify.ngo_app.domain.Payee;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PayeeRepository extends JpaRepository<Payee, Integer> {
}
