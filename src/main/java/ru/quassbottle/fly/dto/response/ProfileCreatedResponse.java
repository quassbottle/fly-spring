package ru.quassbottle.fly.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
public class ProfileCreatedResponse {
    private long id;
    private boolean isWorker;
}
