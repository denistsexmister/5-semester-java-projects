package ua.nure.tsekhmister.users.dao.mysql;

import ua.nure.tsekhmister.users.dao.CarDAO;
import ua.nure.tsekhmister.users.dao.DAOFactory;
import ua.nure.tsekhmister.users.dao.UserDAO;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySqlDAOFactory extends DAOFactory {
    private UserDAO userDAO;
    private CarDAO carDAO;
    private static final String URL_USER;
    private static final String URL_CAR;
    private static final String USER;
    private static final String PASSWORD;

    static {
        Properties prop = new Properties();
        try (InputStream is = MySqlDAOFactory.class.getResourceAsStream("/application.properties")) {
            prop.load(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        URL_USER = prop.getProperty("user.database.url");
        URL_CAR = prop.getProperty("car.database.url");
        USER = prop.getProperty("database.user");
        PASSWORD = prop.getProperty("database.password");

    }

    private static Connection getConnection(String url, boolean autoCommit) throws SQLException {
        Connection con = DriverManager.getConnection(url, USER, PASSWORD);
        con.setAutoCommit(autoCommit);
        if (!autoCommit) {
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        }
        return con;
    }

    static Connection getConnectionToCar() throws SQLException {
        return getConnectionToCar(true);
    }

    static Connection getConnectionToCar(boolean autoCommit) throws SQLException {
        return getConnection(URL_CAR, autoCommit);
    }

    static Connection getConnectionToUser() throws SQLException {
        return getConnectionToUser(true);
    }

    static Connection getConnectionToUser(boolean autoCommit) throws SQLException {
        return getConnection(URL_USER, autoCommit);
    }


    static void close(Connection con) throws SQLException {
        if (con != null) {
            con.close();
        }
    }

    static void rollback(Connection con) throws SQLException {
        if (con != null) {
            con.rollback();
        }
    }

    @Override
    public synchronized UserDAO getUserDAO() {
        if (userDAO == null) {
            userDAO = new MySqlUserDAO();
        }
        return userDAO;
    }

    @Override
    public synchronized CarDAO getCarDAO() {
        if (carDAO == null) {
            carDAO = new MySqlCarDAO();
        }
        return carDAO;
    }
}
