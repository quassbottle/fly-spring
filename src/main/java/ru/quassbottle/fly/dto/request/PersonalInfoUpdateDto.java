package ru.quassbottle.fly.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PersonalInfoUpdateDto {
    private Long accountId;
    private String firstname;
    private String lastname;
    private String username;
}
