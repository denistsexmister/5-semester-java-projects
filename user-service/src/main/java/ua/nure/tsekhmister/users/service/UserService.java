package ua.nure.tsekhmister.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nure.tsekhmister.commons.entity.CarOnSale;
import ua.nure.tsekhmister.commons.entity.User;
import ua.nure.tsekhmister.commons.form.authorization.RegisterForm;
import ua.nure.tsekhmister.users.dao.CarDAO;
import ua.nure.tsekhmister.users.dao.DAOFactory;
import ua.nure.tsekhmister.users.dao.UserDAO;


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
