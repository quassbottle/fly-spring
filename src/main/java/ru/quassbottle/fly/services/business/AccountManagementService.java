package ru.quassbottle.fly.services.business;

import ru.quassbottle.fly.dto.request.PersonalInfoUpdateDto;
import ru.quassbottle.fly.dto.response.ProfileCreatedResponse;

public interface AccountManagementService {
    boolean changePassword(String email, String password);
    boolean changeEmail(String oldEmail, String newEmail);
    boolean updatePersonalInfo(String email, PersonalInfoUpdateDto personalInfoUpdateDto);
    boolean deleteProfile(String email, Long profileId);
    ProfileCreatedResponse createProfile(String email, boolean isWorker);
}
