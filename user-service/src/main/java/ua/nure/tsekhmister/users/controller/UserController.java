package ua.nure.tsekhmister.users.controller;

import org.springframework.web.bind.annotation.*;
import ua.nure.tsekhmister.commons.entity.CarOnSale;
import ua.nure.tsekhmister.commons.entity.User;
import ua.nure.tsekhmister.commons.form.authorization.RegisterForm;
import ua.nure.tsekhmister.users.service.UserService;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/addNewUser")
    public User addNewUser(@RequestBody RegisterForm registerForm) throws SQLException {
        return userService.addNewUser(registerForm);
    }

    @GetMapping("/getUserByLogin/{login}")
    public User getUserByLogin(@PathVariable("login") String login) throws SQLException {
        return userService.getUserByLogin(login);
    }

    @GetMapping("/getCarsByOwner/{id}")
    public List<CarOnSale> getCarsByOwner(@PathVariable("id") Long id) throws SQLException {
        User owner = new User();
        owner.setId(id);

        return userService.getCarsByOwner(owner);
    }

    @GetMapping("/getUserById/{id}")
    public User getUserById(@PathVariable("id") Long id) throws SQLException {
        return userService.getUserById(id);
    }

    @DeleteMapping("/deleteUserById/{id}")
    public void deleteUserById(@PathVariable("id") Long id) throws SQLException{
        User user = new User();
        user.setId(id);

        userService.deleteUserById(user);
    }
}
