package ru.quassbottle.fly.services.business;

import ru.quassbottle.fly.dto.request.PersonalInfoUpdateDto;

public interface AccountManagementService {
    boolean changePassword(String email, String password);
    boolean changeEmail(String oldEmail, String newEmail);
    boolean updatePersonalInfo(String email, PersonalInfoUpdateDto personalInfoUpdateDto);
    boolean deleteProfile(String email, Long profileId);
    boolean createProfile(String email, boolean isWorker);
}
