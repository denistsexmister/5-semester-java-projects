package ua.nure.tsekhmister.cardealership.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import ua.nure.tsekhmister.cardealership.form.AreYouSureForm;
import ua.nure.tsekhmister.cardealership.form.authorization.LogInForm;
import ua.nure.tsekhmister.cardealership.form.authorization.RegisterForm;

public interface IUserController {
    String getRegisterPageView(Model model, HttpServletRequest httpServletRequest);

    String addNewUser(Model model, @Validated RegisterForm registerForm,
                      BindingResult bindingResult, HttpServletRequest httpServletRequest);

    String getLogInPageView(Model model, HttpServletRequest httpServletRequest);

    String logIn(Model model, @Validated LogInForm logInForm,
                 BindingResult bindingResult, HttpServletRequest httpServletRequest);

    String getLoggedUserPage(Model model, HttpServletRequest httpServletRequest);

    String logOut(HttpServletRequest httpServletRequest);

    String getUserPage(Model model, HttpServletRequest httpServletRequest);

    String getDeleteMyAccountPage(Model model, HttpServletRequest httpServletRequest);

    String deleteMyAccount(Model model, AreYouSureForm areYouSureForm,
                           HttpServletRequest httpServletRequest);
}
