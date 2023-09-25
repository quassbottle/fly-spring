package ru.quassbottle.fly.services.business;

import ru.quassbottle.fly.dto.request.PersonalInfoUpdateDto;

public interface ProfileManagementService {
    void createWorkerProfile(String email);
    void createEmployerProfile(String email);

}
