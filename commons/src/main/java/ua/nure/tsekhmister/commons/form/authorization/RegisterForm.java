package ua.nure.tsekhmister.commons.form.authorization;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegisterForm {
    @Valid
    @NotBlank(message = "Login must have some characters!")
    @Size(max = 32, message = "Login must be less than 32 characters!")
    private String login;
    @Valid
    @NotBlank(message = "Password must have some characters!")
    @Size(max = 32, message = "Password must be less than 32 characters!")
    private String password;
    @Valid
    @NotBlank(message = "Full name must have some characters!")
    @Size(max = 100, message = "Password must be less than 100 characters!")
    private String fullName;
    @Valid
    @NotBlank(message = "Phone must have some characters!")
    @Pattern(regexp = "^\\+[0-9]{12}$", message = "Phone must + and 12 numbers after it!")
    private String phone;

    public RegisterForm() {
    }

    public RegisterForm(String login, String password, String fullName, String phone) {
        this.login = login;
        this.password = password;
        this.fullName = fullName;
        this.phone = phone;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
