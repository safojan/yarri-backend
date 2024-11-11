package io.bootify.ngo_app.model;

import io.bootify.ngo_app.views.UserViews;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonView;

@Getter
@Setter
public class UserDTO {

  @JsonView({UserViews.WithPassword.class,UserViews.WithoutPassword.class}) // Included in WithPassword view
  private Integer id;

  @NotNull
  @Size(max = 255)
  @JsonView({UserViews.WithPassword.class, UserViews.WithoutPassword.class}) // Included in both views
  private String name;

  @NotNull
  @Size(max = 255)
  @JsonView({UserViews.WithPassword.class, UserViews.WithoutPassword.class}) // Included in both views
  private String email;

  @NotNull
  @Size(max = 255)
  @JsonView(UserViews.WithPassword.class) // Included in WithPassword view
  private String password;

  @Size(max = 20)
  @JsonView({UserViews.WithPassword.class, UserViews.WithoutPassword.class}) // Included in both views
  private String phone;

  @Size(max = 255)
  @JsonView({UserViews.WithPassword.class, UserViews.WithoutPassword.class}) // Included in both views
  private String address;

  @NotNull
  @JsonView({UserViews.WithPassword.class, UserViews.WithoutPassword.class}) // Included in both views
  private Integer role;
}
