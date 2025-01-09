package ua.nure.tsekhmister.cardealership.dao.collection;

import ua.nure.tsekhmister.cardealership.dao.CarDAO;
import ua.nure.tsekhmister.cardealership.entity.CarOnSale;
import ua.nure.tsekhmister.cardealership.entity.User;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class ListCarDAO implements CarDAO {
    private final List<CarOnSale> carsOnSale = new ArrayList<>();
    @Override
    public CarOnSale addCarOnSale(CarOnSale car) {
        carsOnSale.add((CarOnSale) car.clone());
        return car;
    }

    @Override
    public void deleteCarFromSaleByVin(CarOnSale car) {
        Iterator<CarOnSale> iterator = carsOnSale.iterator();
        while (iterator.hasNext()) {
            CarOnSale listCar = iterator.next();
            if (Objects.equals(car.getVin(), listCar.getVin())) {
                iterator.remove();
                return;
            }
        }
    }


    @Override
    public CarOnSale getCarByVin(String vin) throws SQLException {
        for (CarOnSale car : carsOnSale) {
            if (Objects.equals(car.getVin(), vin)) {
                return car;
            }
        }

        return null;
    }

    @Override
    public List<CarOnSale> getCars() throws SQLException {
        return carsOnSale;
    }

    @Override
    public List<CarOnSale> getCarsByOwner(User user) throws SQLException {
        List<CarOnSale> result = new ArrayList<>();
        for (CarOnSale car : carsOnSale) {
            if (Objects.equals(car.getOwnerId(), user.getId())) {
                result.add(car);
            }
        }

        return result;
    }

    @Override
    public List<CarOnSale> getCarsByBrand(String brand) throws SQLException {
        List<CarOnSale> result = new ArrayList<>();
        for (CarOnSale car : carsOnSale) {
            if (Objects.equals(car.getBrand(), brand)) {
                result.add(car);
            }
        }

        return result;
    }

    @Override
    public List<CarOnSale> getCarsWithPriceBiggerThan(BigDecimal price) throws SQLException {
        List<CarOnSale> result = new ArrayList<>();
        for (CarOnSale car : carsOnSale) {
            if (car.getPrice().compareTo(price) > 0) {
                result.add(car);
            }
        }

        return result;
    }

    @Override
    public CarOnSale changeCarOnSaleBrand(CarOnSale car, String brand) throws SQLException {
        for (CarOnSale listCar : carsOnSale) {
            if (Objects.equals(car, listCar)) {
                listCar.setBrand(brand);
                car.setBrand(brand);
            }
        }

        return car;
    }

    @Override
    public CarOnSale changeCarOnSalePrice(CarOnSale car, BigDecimal price) throws SQLException {
        for (CarOnSale listCar : carsOnSale) {
            if (Objects.equals(car, listCar)) {
                listCar.setPrice(price);
                car.setPrice(price);
            }
        }

        return car;
    }
}
