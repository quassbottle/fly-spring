package ru.quassbottle.fly.helpers;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class AccountFieldValidator {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+\\.[a-z]+$";
    private static final String PASSWORD_REGEX = "^[\\w@$!%*#?&_+-=();':\"<>,./]{8,}$";
    private static final String NAME_REGEX = "^[a-zA-Z]+$|^[а-яА-Я]+$";
    private static final String USERNAME_REGEX = "^[\\w_-]{1,20}$";

    public boolean isValidEmail(String email) {
        return email != null && !email.isBlank() && Pattern.matches(EMAIL_REGEX, email);
    }

    public boolean isValidPassword(String password) {
        return password != null && !password.isBlank() && Pattern.matches(PASSWORD_REGEX, password);
    }

    public boolean isValidName(String name) {
        return name != null && !name.isBlank() && Pattern.matches(NAME_REGEX, name);
    }

    public boolean isValidUsername(String username) {
        return username != null && !username.isBlank() && Pattern.matches(USERNAME_REGEX, username);
    }
}
