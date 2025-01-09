package ua.nure.tsekhmister.cardealership.dao.mysql;

import ua.nure.tsekhmister.cardealership.dao.CarDAO;
import ua.nure.tsekhmister.cardealership.entity.CarOnSale;
import ua.nure.tsekhmister.cardealership.entity.User;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class MySqlCarDAO implements CarDAO {

    @Override
    public CarOnSale addCarOnSale(CarOnSale car) throws SQLException {
        try (Connection conn = MySqlDAOFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO " +
                     "car_on_sale(vin, owner_id, brand, production_year, engine_power, price) values (?, ?, ?, ?, ?, ?)")){
            conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            ps.setString(1, car.getVin());
            ps.setLong(2, car.getOwnerId());
            ps.setString(3, car.getBrand());
            ps.setString(4, car.getProductionYear().toString());
            ps.setBigDecimal(5, car.getEnginePower());
            ps.setBigDecimal(6, car.getPrice());

            ps.executeUpdate();

            return car;
        }
    }

    @Override
    public void deleteCarFromSaleByVin(CarOnSale car) throws SQLException {
        try (Connection conn = MySqlDAOFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM car_on_sale WHERE vin = ?")) {
            conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            ps.setString(1, car.getVin());

            ps.executeUpdate();
        }
    }

    @Override
    public CarOnSale getCarByVin(String vin) throws SQLException {
        try (Connection conn = MySqlDAOFactory.getConnection();
        PreparedStatement ps = conn.prepareStatement(
                "select vin, owner_id, brand, production_year, engine_power, price" +
                        " from car_on_sale where vin = ?")) {
            ps.setString(1, vin);
            try (ResultSet rs = ps.executeQuery()) {
                CarOnSale car = null;
                if (rs.next()) {
                    car = mapCarOnSale(rs);
                }
                return car;
            }
        }
    }

    @Override
    public List<CarOnSale> getCars() throws SQLException {
        try (Connection conn = MySqlDAOFactory.getConnection();
        PreparedStatement ps = conn.prepareStatement(
                "select vin, owner_id, brand, production_year, engine_power, price" +
                " from car_on_sale")) {
            try (ResultSet rs = ps.executeQuery()) {
                List<CarOnSale> cars = new ArrayList<>();
                while (rs.next()) {
                    cars.add(mapCarOnSale(rs));
                }
                return cars;
            }
        }
    }

    private CarOnSale mapCarOnSale(ResultSet rs) throws SQLException {
        CarOnSale car = new CarOnSale();

        car.setVin(rs.getString("vin"));
        car.setOwnerId(rs.getLong("owner_id"));
        car.setBrand(rs.getString("brand"));
        car.setProductionYear(Year.of(rs.getDate("production_year").toLocalDate().getYear()));
        car.setEnginePower(rs.getBigDecimal("engine_power"));
        car.setPrice(rs.getBigDecimal("price"));

        return car;
    }

    @Override
    public List<CarOnSale> getCarsByOwner(User user) throws SQLException {
        try (Connection conn = MySqlDAOFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "select vin, owner_id, brand, production_year, engine_power, price" +
                             " from car_on_sale where owner_id = ?")) {
            ps.setLong(1, user.getId());
            try (ResultSet rs = ps.executeQuery()) {
                List<CarOnSale> cars = new ArrayList<>();
                while (rs.next()) {
                    cars.add(mapCarOnSale(rs));
                }

                return cars;
            }
        }
    }

    @Override
    public List<CarOnSale> getCarsByBrand(String brand) throws SQLException {
        try (Connection conn = MySqlDAOFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "select vin, owner_id, brand, production_year, engine_power, price" +
                             " from car_on_sale where brand = ?")) {
            ps.setString(1, brand);
            try (ResultSet rs = ps.executeQuery()) {
                List<CarOnSale> cars = new ArrayList<>();
                while (rs.next()) {
                    cars.add(mapCarOnSale(rs));
                }

                return cars;
            }
        }
    }

    @Override
    public List<CarOnSale> getCarsWithPriceBiggerThan(BigDecimal price) throws SQLException {
        try (Connection conn = MySqlDAOFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "select vin, owner_id, brand, production_year, engine_power, price" +
                             " from car_on_sale where price > ?")) {
            ps.setBigDecimal(1, price);
            try (ResultSet rs = ps.executeQuery()) {
                List<CarOnSale> cars = new ArrayList<>();
                while (rs.next()) {
                    cars.add(mapCarOnSale(rs));
                }

                return cars;
            }
        }
    }

    @Override
    public CarOnSale changeCarOnSaleBrand(CarOnSale car, String brand) throws SQLException {
        try (Connection conn = MySqlDAOFactory.getConnection();
        PreparedStatement ps = conn.prepareStatement("update car_on_sale set brand = ? where vin = ?;")) {
            conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            ps.setString(1, brand);
            ps.setString(2, car.getVin());

            ps.executeUpdate();
            car.setBrand(brand);
            return car;
        }
    }

    @Override
    public CarOnSale changeCarOnSalePrice(CarOnSale car, BigDecimal price) throws SQLException {
        try (Connection conn = MySqlDAOFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("update car_on_sale set price = ? where vin = ?;")) {
            conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            ps.setBigDecimal(1, price);
            ps.setString(2, car.getVin());

            ps.executeUpdate();
            car.setPrice(price);
            return car;
        }
    }
}
