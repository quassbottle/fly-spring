package ru.quassbottle.fly.controllers.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.quassbottle.fly.entities.Profile;
import ru.quassbottle.fly.services.crud.ProfileService;

import java.util.List;

@RestController
@RequestMapping("/api/service/profiles")
public class ProfileCrudController {
    private final ProfileService profileService;

    @Autowired
    public ProfileCrudController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public List<Profile> getAll() {
        return this.profileService.getAll();
    }

    @GetMapping("{id}")
    public Profile getById(@PathVariable long id) {
        return this.profileService.getById(id);
    }

    @PostMapping
    public Profile save(@RequestBody Profile profile) {
        return this.profileService.save(profile);
    }

    @PutMapping("{id}")
    public Profile update(@RequestBody Profile profile, @PathVariable long id) {
        return this.profileService.update(profile, id);
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable long id) {
        this.profileService.deleteById(id);
        return "Deleted successfully";
    }
}
