package ua.nure.tsekhmister.cardealership.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ua.nure.tsekhmister.cardealership.entity.User;
import ua.nure.tsekhmister.cardealership.form.AreYouSureForm;
import ua.nure.tsekhmister.cardealership.form.authorization.LogInForm;
import ua.nure.tsekhmister.cardealership.form.authorization.RegisterForm;

@Controller
public class UserControllerProxy implements IUserController {
    private final IUserController userController;

    @Autowired
    public UserControllerProxy(IUserController userController) {
        this.userController = userController;
    }

    @Override
    @GetMapping(path = "/register")
    public String getRegisterPageView(Model model, HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getSession().getAttribute("loggedUser") != null) {
            return "redirect:/";
        }

        return userController.getRegisterPageView(model, httpServletRequest);
    }

    @Override
    @PostMapping(path = "/register")
    public String addNewUser(Model model, @Validated RegisterForm registerForm,
                             BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        return userController.addNewUser(model, registerForm, bindingResult, httpServletRequest);
    }

    @Override
    @GetMapping(path = "/logIn")
    public String getLogInPageView(Model model, HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getSession().getAttribute("loggedUser") != null) {
            return "redirect:/";
        }

        return userController.getLogInPageView(model, httpServletRequest);
    }

    @Override
    @PostMapping(path = "/logIn")
    public String logIn(Model model, @Validated LogInForm logInForm,
                        BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        return userController.logIn(model, logInForm, bindingResult, httpServletRequest);
    }

    @Override
    @GetMapping(path = "/myAccount")
    public String getLoggedUserPage(Model model, HttpServletRequest httpServletRequest) {
        User loggedUser = (User) httpServletRequest.getSession().getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/";
        }

        return userController.getLoggedUserPage(model, httpServletRequest);
    }

    @Override
    @PostMapping(path = "/logOut")
    public String logOut(HttpServletRequest httpServletRequest) {
        return userController.logOut(httpServletRequest);
    }

    @Override
    @GetMapping(path = "/user")
    public String getUserPage(Model model, HttpServletRequest httpServletRequest) {
        return userController.getUserPage(model, httpServletRequest);
    }

    @Override
    @GetMapping(path = "/deleteMyAccount")
    public String getDeleteMyAccountPage(Model model, HttpServletRequest httpServletRequest) {
        User loggedUser = (User) httpServletRequest.getSession().getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/";
        }

        return userController.getDeleteMyAccountPage(model, httpServletRequest);
    }

    @Override
    @PostMapping(path = "/deleteMyAccount")
    public String deleteMyAccount(Model model, AreYouSureForm areYouSureForm,
                                  HttpServletRequest httpServletRequest) {
        return userController.deleteMyAccount(model, areYouSureForm, httpServletRequest);
    }
}
