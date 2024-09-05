package io.bootify.ngo_app.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MilestoneDTO {

    private Integer id;

    @NotNull
    @Size(max = 255)
    private String name;

    private String description;

    private LocalDate dueDate;

    private Integer project;

    @NotNull
    private Integer status;

}
