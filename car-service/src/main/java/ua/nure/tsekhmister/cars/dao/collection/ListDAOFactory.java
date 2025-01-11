package ua.nure.tsekhmister.cars.dao.collection;


import ua.nure.tsekhmister.cars.dao.CarDAO;
import ua.nure.tsekhmister.cars.dao.DAOFactory;

public class ListDAOFactory extends DAOFactory {
    private static CarDAO carDAO;

    @Override
    public synchronized CarDAO getCarDAO() {
        if (carDAO == null) {
            carDAO = new ListCarDAO();
        }
        return carDAO;
    }
}
