package ru.quassbottle.fly.controllers.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.quassbottle.fly.dto.request.CreateProfileRequest;
import ru.quassbottle.fly.services.business.AccountManagementService;

@RestController
@RequestMapping("/api/profile")
public class ProfileManagerController {
    private final AccountManagementService accountManagementService;

    @Autowired
    public ProfileManagerController(AccountManagementService accountManagementService) {
        this.accountManagementService = accountManagementService;
    }

    @PostMapping("create")
    public ResponseEntity<?> createProfile(@RequestBody CreateProfileRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(this.accountManagementService.createProfile(email, request.isWorker()));
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleException(ResponseStatusException exception) {
        return ResponseEntity.status(exception.getStatusCode()).body(exception.getBody());
    }
}
