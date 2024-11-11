package io.bootify.ngo_app.model;

import com.fasterxml.jackson.annotation.JsonView;
import io.bootify.ngo_app.views.RoleViews;
import io.bootify.ngo_app.views.UserViews;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RoleDTO {
  @JsonView({RoleViews.basic.class, RoleViews.details.class})
  private Integer id;

    @NotNull
    @Size(max = 255)
    @JsonView({RoleViews.basic.class, RoleViews.details.class})
    private String name;

  @JsonView({RoleViews.details.class})
  private String description;

}
