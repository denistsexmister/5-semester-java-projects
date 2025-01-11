package ua.nure.tsekhmister.users.dao.collection;


import ua.nure.tsekhmister.users.dao.CarDAO;
import ua.nure.tsekhmister.users.dao.DAOFactory;
import ua.nure.tsekhmister.users.dao.UserDAO;

public class ListDAOFactory extends DAOFactory {
    private static UserDAO userDAO;
    private static CarDAO carDAO;

    @Override
    public synchronized UserDAO getUserDAO() {
        if (userDAO == null) {
            userDAO = new ListUserDAO(getCarDAO());
        }
        return userDAO;
    }

    @Override
    public synchronized CarDAO getCarDAO() {
        if (carDAO == null) {
            carDAO = new ListCarDAO();
        }
        return carDAO;
    }
}
