package io.bootify.ngo_app.model;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProjectDetailDTO {

    @Valid
    private ProjectDTO project;

    @Valid
    private ProjectTypeDTO projectType;

    @Valid
    private TaskDTO tasks;

    @Valid
    private MilestoneDTO milestones;

}
