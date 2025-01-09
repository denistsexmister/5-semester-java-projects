package ua.nure.tsekhmister.cardealership.dao.collection;

import ua.nure.tsekhmister.cardealership.dao.CarDAO;
import ua.nure.tsekhmister.cardealership.dao.DAOFactory;
import ua.nure.tsekhmister.cardealership.dao.DealDAO;
import ua.nure.tsekhmister.cardealership.dao.UserDAO;

public class ListDAOFactory extends DAOFactory {
    private static UserDAO userDAO;
    private static CarDAO carDAO;
    private static DealDAO dealDAO;

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

    @Override
    public synchronized DealDAO getDealDAO() {
        if (dealDAO == null) {
            dealDAO = new ListDealDAO(getCarDAO());
        }
        return dealDAO;
    }
}
