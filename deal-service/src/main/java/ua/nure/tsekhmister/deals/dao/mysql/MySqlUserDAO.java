package ua.nure.tsekhmister.deals.dao.mysql;

import ua.nure.tsekhmister.commons.entity.User;
import ua.nure.tsekhmister.deals.dao.UserDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlUserDAO implements UserDAO {
    @Override
    public User addUser(User user) throws SQLException {
        try(Connection conn = MySqlDAOFactory.getConnectionToUser();
            PreparedStatement ps = conn.prepareStatement(
                    "insert into user(login, password, full_name, phone) values (?, ?, ?, ?);",
                    Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFullName());
            ps.setString(4, user.getPhone());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setId(rs.getLong(1));
                }
                return user;
            }
        }
    }

    @Override
    public void deleteUserById(User user) throws SQLException {
        Connection conn = null;
        Connection conn2 = null;
        try {
            conn = MySqlDAOFactory.getConnectionToUser(false);
            conn2 = MySqlDAOFactory.getConnectionToCar(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            deleteUserCars(conn2, user);
            deleteUserById(conn, user);
            conn.commit();
            conn2.commit();
        } catch (SQLException e) {
            MySqlDAOFactory.rollback(conn);
            MySqlDAOFactory.rollback(conn2);
            throw new SQLException(e);
        } finally {
            MySqlDAOFactory.close(conn);
            MySqlDAOFactory.close(conn2);
        }

    }

    @Override
    public void deleteUserByLogin(User user) throws SQLException {
        Connection conn = null;
        Connection conn2 = null;
        try {
            conn = MySqlDAOFactory.getConnectionToUser(false);
            conn2 = MySqlDAOFactory.getConnectionToCar(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            deleteUserCars(conn2, user);
            deleteUserByLogin(conn, user);
            conn.commit();
            conn2.commit();
        } catch (SQLException e) {
            MySqlDAOFactory.rollback(conn);
            MySqlDAOFactory.rollback(conn2);
            throw new SQLException(e);
        } finally {
            MySqlDAOFactory.close(conn);
            MySqlDAOFactory.close(conn2);
        }
    }

    @Override
    public List<User> getUsers() throws SQLException {
        try (Connection conn = MySqlDAOFactory.getConnectionToUser();
        PreparedStatement ps = conn.prepareStatement(
                "select user_id, login, password, full_name, phone from user")) {
            try (ResultSet rs = ps.executeQuery()) {
                List<User> users = new ArrayList<>();
                while (rs.next()) {
                    users.add(mapUser(rs));
                }

                return users;
            }
        }
    }

    private void deleteUserById(Connection conn, User user) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE from user where user_id = ?")) {
            ps.setLong(1, user.getId());
            ps.executeUpdate();
        }
    }

    private void deleteUserByLogin(Connection conn, User user) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE from user where login = ?")) {
            ps.setString(1, user.getLogin());
            ps.executeUpdate();
        }
    }

    private void deleteUserCars(Connection conn, User user) throws SQLException{
        try (PreparedStatement ps = conn.prepareStatement("DELETE from car_on_sale where owner_id = ?")) {
            ps.setLong(1, user.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public User getUserById(Long userId) throws SQLException {
        try(Connection conn = MySqlDAOFactory.getConnectionToUser();
            PreparedStatement ps = conn.prepareStatement("select user_id, login, password," +
                    " full_name, phone from user where user_id = ?")) {
            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                User user = null;
                if (rs.next()) {
                    user = mapUser(rs);
                }

                return user;
            }
        }
    }

    @Override
    public User getUserByLogin(String login) throws SQLException {
        try(Connection conn = MySqlDAOFactory.getConnectionToUser();
            PreparedStatement ps = conn.prepareStatement("select user_id, login, password," +
                    " full_name, phone from user where login = ?")) {
            ps.setString(1, login);
            try (ResultSet rs = ps.executeQuery()) {
                User user = null;
                if (rs.next()) {
                    user = mapUser(rs);
                }

                return user;
            }
        }
    }

    @Override
    public List<User> getUsersByFullName(String fullName) throws SQLException {
        try (Connection conn = MySqlDAOFactory.getConnectionToUser();
        PreparedStatement ps = conn.prepareStatement("select user_id, login, password," +
                " full_name, phone from user where full_name = ?")) {
            ps.setString(1, fullName);
            try (ResultSet rs = ps.executeQuery()) {
                List<User> users = new ArrayList<>();
                while (rs.next()) {
                    users.add(mapUser(rs));
                }

                return users;
            }
        }
    }

    @Override
    public User changeUserFullName(User user, String fullName) throws SQLException {
        try (Connection conn = MySqlDAOFactory.getConnectionToUser();
        PreparedStatement ps = conn.prepareStatement("update user set full_name = ? where user_id = ?;")) {
            conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            ps.setString(1, fullName);
            ps.setLong(2, user.getId());

            ps.executeUpdate();
            user.setFullName(fullName);
            return user;
        }
    }

    @Override
    public User changeUserPassword(User user, String password) throws SQLException {
        try (Connection conn = MySqlDAOFactory.getConnectionToUser();
             PreparedStatement ps = conn.prepareStatement("update user set password = ? where user_id = ?;")) {
            conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            ps.setString(1, password);
            ps.setLong(2, user.getId());

            ps.executeUpdate();
            user.setPassword(password);
            return user;
        }
    }

    private User mapUser(ResultSet rs) throws SQLException {
        User user = new User();

        user.setId(rs.getLong("user_id"));
        user.setLogin(rs.getString("login"));
        user.setPassword(rs.getString("password"));
        user.setFullName(rs.getString("full_name"));
        user.setPhone(rs.getString("phone"));

        return user;
    }
}
