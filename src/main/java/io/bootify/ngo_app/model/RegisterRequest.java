package io.bootify.ngo_app.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RegisterRequest {
    String name;
    String email;
    String password;
    String phone;
    String address;
}
