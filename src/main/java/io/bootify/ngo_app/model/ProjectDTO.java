package io.bootify.ngo_app.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProjectDTO {

    private Integer id;

    @NotNull
    @Size(max = 255)
    private String name;

    private String description;

    @Digits(integer = 17, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal goalAmount;

    @Digits(integer = 17, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal raisedAmount;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer createdBy;

    private Integer type;

    @NotNull
    private Integer status;

    private Integer user;

}
