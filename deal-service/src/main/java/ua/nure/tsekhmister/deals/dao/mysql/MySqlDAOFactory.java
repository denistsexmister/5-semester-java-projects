package ua.nure.tsekhmister.deals.dao.mysql;

import ua.nure.tsekhmister.deals.dao.DAOFactory;
import ua.nure.tsekhmister.deals.dao.DealDAO;
import ua.nure.tsekhmister.deals.dao.UserDAO;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySqlDAOFactory extends DAOFactory {
    private UserDAO userDAO;
    private DealDAO dealDAO;
    private static final String URL_USER;
    private static final String URL_DEAL;
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
        URL_DEAL = prop.getProperty("deal.database.url");
        URL_CAR = prop.getProperty("car.database.url");
        USER = prop.getProperty("database.user");
        PASSWORD = prop.getProperty("database.password");

    }

    static Connection getConnectionToUser() throws SQLException {
        return getConnectionToUser(true);
    }

    static Connection getConnectionToUser(boolean autoCommit) throws SQLException {
        return getConnection(URL_USER, autoCommit);
    }

    static Connection getConnectionToDeal() throws SQLException {
        return getConnectionToDeal(true);
    }

    static Connection getConnectionToDeal(boolean autoCommit) throws SQLException {
        return getConnection(URL_DEAL, autoCommit);
    }

    static Connection getConnectionToCar() throws SQLException {
        return getConnectionToCar(true);
    }

    static Connection getConnectionToCar(boolean autoCommit) throws SQLException {
        return getConnection(URL_CAR, autoCommit);
    }

    private static Connection getConnection(String url, boolean autoCommit) throws SQLException {
        Connection con = DriverManager.getConnection(url, USER, PASSWORD);
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
    public synchronized DealDAO getDealDAO() {
        if (dealDAO == null) {
            dealDAO = new MySqlDealDAO();
        }
        return dealDAO;
    }
}
