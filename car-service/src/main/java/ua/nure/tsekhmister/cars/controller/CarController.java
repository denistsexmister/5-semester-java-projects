package ua.nure.tsekhmister.cars.controller;

import org.springframework.web.bind.annotation.*;
import ua.nure.tsekhmister.cars.service.CarService;
import ua.nure.tsekhmister.commons.entity.CarOnSale;
import ua.nure.tsekhmister.commons.form.AddCarOnSaleForm;


import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/getCars")
    public List<CarOnSale> getCars() throws SQLException {
        return carService.getCars();
    }

    @GetMapping("/getCarByVin/{vin}")
    public CarOnSale getCarByVin(@PathVariable("vin") String vin) throws SQLException {
        return carService.getCarByVin(vin);
    }

    @GetMapping("/getCarsByBrand/{brand}")
    public List<CarOnSale> getCarsByBrand(@PathVariable("brand") String brand) throws SQLException {
        return carService.getCarsByBrand(brand);
    }

    @GetMapping("/getCarsWithPriceBiggerThan/{price}")
    public List<CarOnSale> getCarsWithPriceBiggerThan(@PathVariable("price") BigDecimal price) throws SQLException {
        return carService.getCarsWithPriceBiggerThan(price);
    }

    @PostMapping("/addCarOnSale")
    public CarOnSale addCarOnSale(AddCarOnSaleForm addCarOnSaleForm, Long ownerId) throws SQLException {
        return carService.addCarOnSale(addCarOnSaleForm, ownerId);
    }

    @DeleteMapping("/deleteCarFromSaleByVin")
    public void deleteCarFromSaleByVin(@RequestBody CarOnSale car) throws SQLException {
        carService.deleteCarFromSaleByVin(car);
    }
}
