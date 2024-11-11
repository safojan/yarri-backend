package io.bootify.ngo_app.model.CustomDTO;
import io.bootify.ngo_app.model.UserPermissionDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PermissionsDTO extends UserPermissionDTO {
    private String permissionName;
}
