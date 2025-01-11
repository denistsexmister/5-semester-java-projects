package ua.nure.tsekhmister.users.dao;

import ua.nure.tsekhmister.commons.entity.CarOnSale;
import ua.nure.tsekhmister.commons.entity.User;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface CarDAO {
    CarOnSale addCarOnSale(CarOnSale car) throws SQLException;
    void deleteCarFromSaleByVin(CarOnSale car) throws SQLException;
    CarOnSale getCarByVin(String vin) throws SQLException;
    List<CarOnSale> getCars() throws SQLException;
    List<CarOnSale> getCarsByOwner(User user) throws SQLException;
    List<CarOnSale> getCarsByBrand(String brand) throws SQLException;
    List<CarOnSale> getCarsWithPriceBiggerThan(BigDecimal price) throws SQLException;
    CarOnSale changeCarOnSaleBrand(CarOnSale car, String brand) throws SQLException;
    CarOnSale changeCarOnSalePrice(CarOnSale car, BigDecimal price) throws SQLException;
}
