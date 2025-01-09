package ua.nure.tsekhmister.cardealership.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nure.tsekhmister.cardealership.dao.CarDAO;
import ua.nure.tsekhmister.cardealership.dao.DAOFactory;
import ua.nure.tsekhmister.cardealership.dao.UserDAO;
import ua.nure.tsekhmister.cardealership.entity.CarOnSale;
import ua.nure.tsekhmister.cardealership.entity.User;
import ua.nure.tsekhmister.cardealership.form.authorization.RegisterForm;

import java.sql.SQLException;
import java.util.List;

@Service
public class UserService {
    private final UserDAO userDAO;
    private final CarDAO carDAO;

    @Autowired
    public UserService(DAOFactory daoFactory) {
        this.userDAO = daoFactory.getUserDAO();
        this.carDAO = daoFactory.getCarDAO();
    }

    public User addNewUser(RegisterForm registerForm) throws SQLException {
        return userDAO.addUser(new User(registerForm.getLogin(), registerForm.getPassword(),
                registerForm.getFullName(), registerForm.getPhone()));
    }

    public User getUserByLogin(String login) throws SQLException {
        return userDAO.getUserByLogin(login);
    }

    public List<CarOnSale> getCarsByOwner(User owner) throws SQLException {
        return carDAO.getCarsByOwner(owner);
    }

    public User getUserById(Long id) throws SQLException {
        return userDAO.getUserById(id);
    }

    public void deleteUserById(User user) throws SQLException{
        userDAO.deleteUserById(user);
    }
}
