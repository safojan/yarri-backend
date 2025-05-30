package io.bootify.ngo_app.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PermissionDTO {

    private Integer id;

    @NotNull
    @Size(max = 255)
    private String name;

    private String description;

}
