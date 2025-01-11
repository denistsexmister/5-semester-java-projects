package ua.nure.tsekhmister.controllers.client;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import ua.nure.tsekhmister.commons.entity.CarOnSale;
import ua.nure.tsekhmister.commons.entity.User;
import ua.nure.tsekhmister.commons.form.authorization.RegisterForm;

import java.util.List;

@HttpExchange(url = "/users")
public interface UserClient {
    @GetExchange("/getUserById/{id}")
    User getUserById(@PathVariable("id") Long id);

    @GetExchange("/getUserByLogin/{login}")
    User getUserByLogin(@PathVariable("login") String login);

    @GetExchange("/getCarsByOwner/{id}")
    List<CarOnSale> getCarsByOwner(@PathVariable("id") Long id);

    @PostExchange("/addNewUser")
    User addNewUser(@RequestBody RegisterForm registerForm);

    @DeleteExchange("/deleteUserById/{id}")
    void deleteUserById(@PathVariable("id") Long id);
}
