package ru.quassbottle.fly.controllers.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.quassbottle.fly.entities.Account;
import ru.quassbottle.fly.services.crud.AccountService;

import java.util.List;

@RestController
@RequestMapping("/api/service/accounts/")
public class AccountCrudController {
    private final AccountService accountService;

    @Autowired
    public AccountCrudController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public List<Account> getAll() {
        return this.accountService.getAll();
    }

    @GetMapping("{id}")
    public Account getById(@PathVariable long id) {
        return this.accountService.getById(id);
    }

    @PostMapping
    public Account save(@RequestBody Account account) {
        return this.accountService.save(account);
    }

    @PutMapping("{id}")
    public Account update(@RequestBody Account account, @PathVariable long id) {
        return this.accountService.update(account, id);
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable long id) {
        this.accountService.deleteById(id);
        return "Deleted successfully";
    }
}
