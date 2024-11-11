package io.bootify.ngo_app.model.CustomDTO;

import io.bootify.ngo_app.model.PermissionDTO;
import io.bootify.ngo_app.model.RoleDTO;
import io.bootify.ngo_app.model.UserDTO;

import com.fasterxml.jackson.annotation.JsonView;

import io.bootify.ngo_app.views.PermissionsView;
import io.bootify.ngo_app.views.RoleViews;
import io.bootify.ngo_app.views.UserViews;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDetailsDTO {
  //detail of a user without password
  @JsonView(UserViews.WithoutPassword.class)
  private UserDTO user;
  @JsonView(RoleViews.basic.class)
  private RoleDTO role;

}
