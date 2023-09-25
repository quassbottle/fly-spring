package ru.quassbottle.fly.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest extends LoginRequest {
    private String firstname;
    private String lastname;
    private String username;
}
