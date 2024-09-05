package io.bootify.ngo_app.service;

import io.bootify.ngo_app.domain.AcademicRecord;
import io.bootify.ngo_app.domain.ChildProfile;
import io.bootify.ngo_app.model.AcademicRecordDTO;
import io.bootify.ngo_app.repos.AcademicRecordRepository;
import io.bootify.ngo_app.repos.ChildProfileRepository;
import io.bootify.ngo_app.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class AcademicRecordService {

    private final AcademicRecordRepository academicRecordRepository;
    private final ChildProfileRepository childProfileRepository;

    public AcademicRecordService(final AcademicRecordRepository academicRecordRepository,
            final ChildProfileRepository childProfileRepository) {
        this.academicRecordRepository = academicRecordRepository;
        this.childProfileRepository = childProfileRepository;
    }

    public List<AcademicRecordDTO> findAll() {
        final List<AcademicRecord> academicRecords = academicRecordRepository.findAll(Sort.by("id"));
        return academicRecords.stream()
                .map(academicRecord -> mapToDTO(academicRecord, new AcademicRecordDTO()))
                .toList();
    }

    public AcademicRecordDTO get(final Integer id) {
        return academicRecordRepository.findById(id)
                .map(academicRecord -> mapToDTO(academicRecord, new AcademicRecordDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final AcademicRecordDTO academicRecordDTO) {
        final AcademicRecord academicRecord = new AcademicRecord();
        mapToEntity(academicRecordDTO, academicRecord);
        return academicRecordRepository.save(academicRecord).getId();
    }

    public void update(final Integer id, final AcademicRecordDTO academicRecordDTO) {
        final AcademicRecord academicRecord = academicRecordRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(academicRecordDTO, academicRecord);
        academicRecordRepository.save(academicRecord);
    }

    public void delete(final Integer id) {
        academicRecordRepository.deleteById(id);
    }

    private AcademicRecordDTO mapToDTO(final AcademicRecord academicRecord,
            final AcademicRecordDTO academicRecordDTO) {
        academicRecordDTO.setId(academicRecord.getId());
        academicRecordDTO.setAcademicYear(academicRecord.getAcademicYear());
        academicRecordDTO.setMarks(academicRecord.getMarks());
        academicRecordDTO.setChildProfile(academicRecord.getChildProfile() == null ? null : academicRecord.getChildProfile().getId());
        return academicRecordDTO;
    }

    private AcademicRecord mapToEntity(final AcademicRecordDTO academicRecordDTO,
            final AcademicRecord academicRecord) {
        academicRecord.setAcademicYear(academicRecordDTO.getAcademicYear());
        academicRecord.setMarks(academicRecordDTO.getMarks());
        final ChildProfile childProfile = academicRecordDTO.getChildProfile() == null ? null : childProfileRepository.findById(academicRecordDTO.getChildProfile())
                .orElseThrow(() -> new NotFoundException("childProfile not found"));
        academicRecord.setChildProfile(childProfile);
        return academicRecord;
    }

}
