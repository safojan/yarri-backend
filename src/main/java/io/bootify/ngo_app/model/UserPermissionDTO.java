package io.bootify.ngo_app.model;

import com.fasterxml.jackson.annotation.JsonView;
import io.bootify.ngo_app.views.UserViews;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserPermissionDTO {

  @JsonView({UserViews.WithPassword.class, UserViews.WithoutPassword.class})
  private Integer id;
  @JsonView({UserViews.WithPassword.class, UserViews.WithoutPassword.class})
  private Integer user;
  @JsonView({UserViews.WithPassword.class, UserViews.WithoutPassword.class})
  private Integer permission;

}
