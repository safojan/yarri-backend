package io.bootify.ngo_app.repos;

import io.bootify.ngo_app.domain.Project;
import io.bootify.ngo_app.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    Transaction findFirstByProject(Project project);

}
