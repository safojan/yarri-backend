package io.bootify.ngo_app.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProjectTypeDTO {

    private Integer id;

    @NotNull
    @Size(max = 255)
    private String name;

}
