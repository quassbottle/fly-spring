package ru.quassbottle.fly.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class AccountRegisteredResponse {
    private long id;
    private String email;
    private Timestamp registeredAt;
}
