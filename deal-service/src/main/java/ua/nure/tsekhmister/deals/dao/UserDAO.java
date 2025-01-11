package ua.nure.tsekhmister.deals.dao;

import ua.nure.tsekhmister.commons.entity.User;

import java.sql.SQLException;
import java.util.List;


public interface UserDAO {
    User addUser(User user) throws SQLException;

    void deleteUserById(User user) throws SQLException;

    void deleteUserByLogin(User user) throws SQLException;

    List<User> getUsers() throws SQLException;

    User getUserById(Long userId) throws SQLException;

    User getUserByLogin(String login) throws SQLException;

    List<User> getUsersByFullName(String fullName) throws SQLException;

    User changeUserFullName(User user, String fullName) throws SQLException;

    User changeUserPassword(User user, String password) throws SQLException;
}