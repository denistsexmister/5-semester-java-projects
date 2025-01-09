package ua.nure.tsekhmister.cardealership.form.authorization;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LogInForm {
    @Valid
    @NotBlank(message = "Login must have some characters!")
    @Size(max = 32, message = "Login can't have bigger than 32 characters")
    private String login;
    @Valid
    @NotBlank(message = "Password must have some characters!")
    @Size(max = 32, message = "Password can't have bigger than 32 characters")
    private String password;

    public LogInForm() {
    }

    public LogInForm(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
