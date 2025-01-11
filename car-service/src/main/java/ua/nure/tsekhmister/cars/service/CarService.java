package ua.nure.tsekhmister.cars.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nure.tsekhmister.cars.dao.CarDAO;
import ua.nure.tsekhmister.cars.dao.DAOFactory;
import ua.nure.tsekhmister.commons.entity.CarOnSale;
import ua.nure.tsekhmister.commons.form.AddCarOnSaleForm;


import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@Service
public class CarService {
    private final CarDAO carDAO;

    @Autowired
    public CarService(DAOFactory daoFactory) {
        this.carDAO = daoFactory.getCarDAO();
    }

    public List<CarOnSale> getCars() throws SQLException {
        return carDAO.getCars();
    }

    public CarOnSale getCarByVin(String vin) throws SQLException {
        return carDAO.getCarByVin(vin);
    }

    public List<CarOnSale> getCarsByBrand(String brand) throws SQLException {
        return carDAO.getCarsByBrand(brand);
    }

    public List<CarOnSale> getCarsWithPriceBiggerThan(BigDecimal price) throws SQLException {
        return carDAO.getCarsWithPriceBiggerThan(price);
    }

    public CarOnSale addCarOnSale(AddCarOnSaleForm addCarOnSaleForm, Long ownerId) throws SQLException {
        return carDAO.addCarOnSale(new CarOnSale(addCarOnSaleForm.getVin(), ownerId,
                addCarOnSaleForm.getBrand(), addCarOnSaleForm.getProductionYear(),
                addCarOnSaleForm.getEnginePower(), addCarOnSaleForm.getPrice()));
    }

    public void deleteCarFromSaleByVin(CarOnSale car) throws SQLException {
        carDAO.deleteCarFromSaleByVin(car);
    }
}
