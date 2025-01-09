package ua.nure.tsekhmister.cardealership.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ua.nure.tsekhmister.cardealership.entity.CarOnSale;
import ua.nure.tsekhmister.cardealership.entity.User;
import ua.nure.tsekhmister.cardealership.form.AreYouSureForm;
import ua.nure.tsekhmister.cardealership.form.authorization.LogInForm;
import ua.nure.tsekhmister.cardealership.form.authorization.RegisterForm;
import ua.nure.tsekhmister.cardealership.service.UserService;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/register")
    public String getRegisterPageView(Model model, HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getSession().getAttribute("loggedUser") != null) {
            return "redirect:/";
        }
        model.addAttribute("registerForm", new RegisterForm());
        return "authorizationPages/registerPage";
    }

    @PostMapping(path = "/register")
    public String addNewUser(Model model, @Validated RegisterForm registerForm,
                             BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("registerForm", registerForm);
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "authorizationPages/registerPage";
        }

        try {
            User user = userService.addNewUser(registerForm);
            httpServletRequest.getSession().setAttribute("loggedUser", user);

            return "redirect:/";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(path = "/logIn")
    public String getLogInPageView(Model model, HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getSession().getAttribute("loggedUser") != null) {
            return "redirect:/";
        }

        model.addAttribute("logInForm", new LogInForm());
        return "authorizationPages/logInPage";
    }

    @PostMapping(path = "/logIn")
    public String logIn(Model model, @Validated LogInForm logInForm,
                        BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("logInForm", logInForm);
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "authorizationPages/logInPage";
        }

        try {
            User user = userService.getUserByLogin(logInForm.getLogin());

            if (user != null ) { // such user is found
                if (Objects.equals(user.getPassword(), logInForm.getPassword())) { // password is correct
                    httpServletRequest.getSession().setAttribute("loggedUser", user);
                    return "redirect:/";
                }
                // password is incorrect
                bindingResult.addError(new ObjectError("logInForm", "Password is incorrect!"));
            } else { // user not found
                bindingResult.addError(new ObjectError("logInForm", "User with such login is not found!"));
            }

            model.addAttribute("logInForm", logInForm);
            model.addAttribute("errors", bindingResult.getAllErrors());

            return "authorizationPages/logInPage";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping(path = "/myAccount")
    public String getLoggedUserPage(Model model, HttpServletRequest httpServletRequest) {
        User loggedUser = (User) httpServletRequest.getSession().getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/";
        }

        try {
            model.addAttribute("userCars", userService.getCarsByOwner(loggedUser));

            return "userPages/loggedUserPage";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping(path = "/logOut")
    public String logOut(HttpServletRequest httpServletRequest) {
        httpServletRequest.getSession().removeAttribute("loggedUser");
        return "redirect:/";
    }

    @GetMapping(path = "/user")
    public String getUserPage(Model model, HttpServletRequest httpServletRequest) {
        String idString = httpServletRequest.getParameter("id");
        if (idString == null) {
            return "redirect:/";
        }
        Long id = Long.valueOf(idString);
        if (httpServletRequest.getSession().getAttribute("loggedUser") != null && Objects.equals(((User) httpServletRequest.getSession().getAttribute("loggedUser")).getId(), id)) {
            return "redirect:/myAccount";
        }

        try {
            User user = userService.getUserById(id);
            List<CarOnSale> carsOnSale = userService.getCarsByOwner(user);
            model.addAttribute("user", user);
            model.addAttribute("carsOnSale", carsOnSale);

            return "userPages/userPage";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(path = "/deleteMyAccount")
    public String getDeleteMyAccountPage(Model model, HttpServletRequest httpServletRequest) {
        User loggedUser = (User) httpServletRequest.getSession().getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/";
        }
        model.addAttribute("areYouSureForm", new AreYouSureForm());
        return "deletingPages/deleteMyAccountPage";
    }

    @PostMapping(path = "/deleteMyAccount")
    public String deleteMyAccount(Model model, AreYouSureForm areYouSureForm,
                                  HttpServletRequest httpServletRequest) {
        User loggedUser = (User) httpServletRequest.getSession().getAttribute("loggedUser");

        if (!areYouSureForm.isSure()) {
            return "redirect:/myAccount";
        }

        try {
            userService.deleteUserById(loggedUser);
            httpServletRequest.getSession().removeAttribute("loggedUser");

            return "redirect:/";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
