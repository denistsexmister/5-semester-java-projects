package ua.nure.tsekhmister.cars.dao;



import ua.nure.tsekhmister.cars.dao.mysql.MySqlDAOFactory;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public abstract class DAOFactory {
    static Map<String, DAOFactory> cache = new HashMap<>();

    public static DAOFactory getDAOFactory() {
        // read configuration
        return new MySqlDAOFactory();
    }

    public static DAOFactory getDAOFactory(String name) throws SQLException {
        try {
            DAOFactory daoFactory = cache.get(name);
            if (daoFactory == null) {
                Class<?> daoClass = Class.forName(name);
                daoFactory = (DAOFactory) daoClass.getDeclaredConstructor().newInstance();
                cache.put(name, daoFactory);
            }
            return daoFactory;
        } catch (ReflectiveOperationException e) {
            throw new SQLException(e);
        }
    }

    public abstract CarDAO getCarDAO();
}
