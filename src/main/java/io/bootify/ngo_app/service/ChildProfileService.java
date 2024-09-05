package io.bootify.ngo_app.service;

import io.bootify.ngo_app.domain.AcademicRecord;
import io.bootify.ngo_app.domain.ChildProfile;
import io.bootify.ngo_app.domain.Project;
import io.bootify.ngo_app.model.ChildProfileDTO;
import io.bootify.ngo_app.repos.AcademicRecordRepository;
import io.bootify.ngo_app.repos.ChildProfileRepository;
import io.bootify.ngo_app.repos.ProjectRepository;
import io.bootify.ngo_app.util.NotFoundException;
import io.bootify.ngo_app.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ChildProfileService {

    private final ChildProfileRepository childProfileRepository;
    private final ProjectRepository projectRepository;
    private final AcademicRecordRepository academicRecordRepository;

    public ChildProfileService(final ChildProfileRepository childProfileRepository,
            final ProjectRepository projectRepository,
            final AcademicRecordRepository academicRecordRepository) {
        this.childProfileRepository = childProfileRepository;
        this.projectRepository = projectRepository;
        this.academicRecordRepository = academicRecordRepository;
    }

    public List<ChildProfileDTO> findAll() {
        final List<ChildProfile> childProfiles = childProfileRepository.findAll(Sort.by("id"));
        return childProfiles.stream()
                .map(childProfile -> mapToDTO(childProfile, new ChildProfileDTO()))
                .toList();
    }

    public ChildProfileDTO get(final Integer id) {
        return childProfileRepository.findById(id)
                .map(childProfile -> mapToDTO(childProfile, new ChildProfileDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final ChildProfileDTO childProfileDTO) {
        final ChildProfile childProfile = new ChildProfile();
        mapToEntity(childProfileDTO, childProfile);
        return childProfileRepository.save(childProfile).getId();
    }

    public void update(final Integer id, final ChildProfileDTO childProfileDTO) {
        final ChildProfile childProfile = childProfileRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(childProfileDTO, childProfile);
        childProfileRepository.save(childProfile);
    }

    public void delete(final Integer id) {
        childProfileRepository.deleteById(id);
    }

    private ChildProfileDTO mapToDTO(final ChildProfile childProfile,
            final ChildProfileDTO childProfileDTO) {
        childProfileDTO.setId(childProfile.getId());
        childProfileDTO.setName(childProfile.getName());
        childProfileDTO.setAge(childProfile.getAge());
        childProfileDTO.setGender(childProfile.getGender());
        childProfileDTO.setClassLevel(childProfile.getClassLevel());
        childProfileDTO.setProject(childProfile.getProject() == null ? null : childProfile.getProject().getId());
        return childProfileDTO;
    }

    private ChildProfile mapToEntity(final ChildProfileDTO childProfileDTO,
            final ChildProfile childProfile) {
        childProfile.setName(childProfileDTO.getName());
        childProfile.setAge(childProfileDTO.getAge());
        childProfile.setGender(childProfileDTO.getGender());
        childProfile.setClassLevel(childProfileDTO.getClassLevel());
        final Project project = childProfileDTO.getProject() == null ? null : projectRepository.findById(childProfileDTO.getProject())
                .orElseThrow(() -> new NotFoundException("project not found"));
        childProfile.setProject(project);
        return childProfile;
    }

    public ReferencedWarning getReferencedWarning(final Integer id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final ChildProfile childProfile = childProfileRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final AcademicRecord childProfileAcademicRecord = academicRecordRepository.findFirstByChildProfile(childProfile);
        if (childProfileAcademicRecord != null) {
            referencedWarning.setKey("childProfile.academicRecord.childProfile.referenced");
            referencedWarning.addParam(childProfileAcademicRecord.getId());
            return referencedWarning;
        }
        return null;
    }

}
