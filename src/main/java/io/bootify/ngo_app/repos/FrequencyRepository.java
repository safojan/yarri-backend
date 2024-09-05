package io.bootify.ngo_app.repos;

import io.bootify.ngo_app.domain.Frequency;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FrequencyRepository extends JpaRepository<Frequency, Integer> {
}
