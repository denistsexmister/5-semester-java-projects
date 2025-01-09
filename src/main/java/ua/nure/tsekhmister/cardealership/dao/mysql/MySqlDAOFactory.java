package ua.nure.tsekhmister.cardealership.dao.mysql;

import ua.nure.tsekhmister.cardealership.dao.CarDAO;
import ua.nure.tsekhmister.cardealership.dao.DAOFactory;
import ua.nure.tsekhmister.cardealership.dao.DealDAO;
import ua.nure.tsekhmister.cardealership.dao.UserDAO;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySqlDAOFactory extends DAOFactory {
    private UserDAO userDAO;
    private CarDAO carDAO;
    private DealDAO dealDAO;
    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    static {
        Properties prop = new Properties();
        try (FileReader fr = new FileReader("src/main/resources/application.properties")) {
            prop.load(fr);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        URL = prop.getProperty("database.url");
        USER = prop.getProperty("database.user");
        PASSWORD = prop.getProperty("database.password");

    }

    static Connection getConnection() throws SQLException {
        return getConnection(true);
    }

    static Connection getConnection(boolean autoCommit) throws SQLException {
        Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
        con.setAutoCommit(autoCommit);
        if (!autoCommit) {
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        }
        return con;
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

    @Override
    public synchronized DealDAO getDealDAO() {
        if (dealDAO == null) {
            dealDAO = new MySqlDealDAO();
        }
        return dealDAO;
    }
}
