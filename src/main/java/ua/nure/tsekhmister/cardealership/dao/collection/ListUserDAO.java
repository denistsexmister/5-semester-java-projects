package ua.nure.tsekhmister.cardealership.dao.collection;

import ua.nure.tsekhmister.cardealership.dao.CarDAO;
import ua.nure.tsekhmister.cardealership.dao.UserDAO;
import ua.nure.tsekhmister.cardealership.entity.CarOnSale;
import ua.nure.tsekhmister.cardealership.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class ListUserDAO implements UserDAO {
    private static final List<User> users = new ArrayList<>();
    private static CarDAO carDAO;
    private static final AtomicLong userIdCounter = new AtomicLong(1L);

    public ListUserDAO(CarDAO carDAOPassed) {
        carDAO = carDAOPassed;
    }

    @Override
    public User addUser(User user) throws SQLException {
        user.setId(userIdCounter.getAndIncrement());
        users.add((User) user.clone());

        return user;
    }

    @Override
    public void deleteUserById(User user) throws SQLException {
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
            User listUser = iterator.next();
            if (Objects.equals(user.getId(), listUser.getId())) {
                List<CarOnSale> ownerCars = carDAO.getCarsByOwner(user);
                for (CarOnSale ownerCar : ownerCars) {
                    carDAO.deleteCarFromSaleByVin(ownerCar);
                }

                iterator.remove();
                return;
            }
        }
    }

    @Override
    public void deleteUserByLogin(User user) throws SQLException {
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
            User listUser = iterator.next();
            if (Objects.equals(user.getLogin(), listUser.getLogin())) {
                List<CarOnSale> ownerCars = carDAO.getCarsByOwner(user);
                for (CarOnSale ownerCar : ownerCars) {
                    carDAO.deleteCarFromSaleByVin(ownerCar);
                }

                iterator.remove();
                return;
            }
        }
    }

    @Override
    public List<User> getUsers() throws SQLException {
        return users;
    }

    @Override
    public User getUserById(Long userId) throws SQLException {
        for (User user : users) {
            if (Objects.equals(user.getId(), userId)) {
                return user;
            }
        }

        return null;
    }

    @Override
    public User getUserByLogin(String login) throws SQLException {
        for (User user : users) {
            if (Objects.equals(user.getLogin(), login)) {
                return user;
            }
        }

        return null;
    }

    @Override
    public List<User> getUsersByFullName(String fullName) throws SQLException {
        List<User> result = new ArrayList<>();
        for (User user : users) {
            if (Objects.equals(user.getFullName(), fullName)) {
                result.add(user);
            }
        }

        return result;
    }

    @Override
    public User changeUserFullName(User user, String fullName) throws SQLException {
        for (User listUser : users) {
            if (Objects.equals(user, listUser)) {
                listUser.setFullName(fullName);
                user.setFullName(fullName);
            }
        }

        return user;
    }

    @Override
    public User changeUserPassword(User user, String password) throws SQLException {
        for (User listUser : users) {
            if (Objects.equals(user, listUser)) {
                listUser.setPassword(password);
                user.setPassword(password);
            }
        }

        return user;
    }
}
