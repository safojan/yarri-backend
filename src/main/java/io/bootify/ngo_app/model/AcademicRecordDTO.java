package io.bootify.ngo_app.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AcademicRecordDTO {

    private Integer id;

    @Size(max = 50)
    private String academicYear;

    private String marks;

    private Integer childProfile;

}
