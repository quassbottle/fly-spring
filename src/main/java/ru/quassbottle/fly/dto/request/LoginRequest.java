package ru.quassbottle.fly.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRegisterRequest {
    private String email;
    private String password;
}
