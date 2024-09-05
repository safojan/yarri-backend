package io.bootify.ngo_app.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserPermissionDTO {

    private Integer id;
    private Integer user;
    private Integer permission;

}
