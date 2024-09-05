package io.bootify.ngo_app.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ChildProfileDTO {

    private Integer id;

    @Size(max = 255)
    private String name;

    private Integer age;

    @Size(max = 10)
    private String gender;

    @Size(max = 255)
    private String classLevel;

    private Integer project;

}
