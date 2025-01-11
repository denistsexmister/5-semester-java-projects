package ua.nure.tsekhmister.cars.dao.mysql;



import ua.nure.tsekhmister.cars.dao.CarDAO;
import ua.nure.tsekhmister.cars.dao.DAOFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySqlDAOFactory extends DAOFactory {
    private CarDAO carDAO;
    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    static {
        Properties prop = new Properties();
        try (InputStream is = MySqlDAOFactory.class.getResourceAsStream("/application.properties")) {
            prop.load(is);
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
    public synchronized CarDAO getCarDAO() {
        if (carDAO == null) {
            carDAO = new MySqlCarDAO();
        }
        return carDAO;
    }
}
