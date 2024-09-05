package io.bootify.ngo_app.repos;

import io.bootify.ngo_app.domain.AcademicRecord;
import io.bootify.ngo_app.domain.ChildProfile;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AcademicRecordRepository extends JpaRepository<AcademicRecord, Integer> {

    AcademicRecord findFirstByChildProfile(ChildProfile childProfile);

}
