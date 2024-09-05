package io.bootify.ngo_app.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DonationDTO {

    private Integer id;

    @Digits(integer = 17, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal amount;

    @Size(max = 10)
    private String currency;

    private Integer user;

    private Integer project;

    private Integer status;

}
