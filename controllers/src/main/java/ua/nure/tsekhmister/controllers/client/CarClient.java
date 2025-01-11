package ua.nure.tsekhmister.controllers.client;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import ua.nure.tsekhmister.commons.entity.Car;
import ua.nure.tsekhmister.commons.entity.CarOnSale;
import ua.nure.tsekhmister.commons.form.AddCarOnSaleForm;

import java.math.BigDecimal;
import java.util.List;

@HttpExchange(url = "/cars")
public interface CarClient {
    @GetExchange("/getCars")
    List<Car> getCars();

    @GetExchange("/getCarByVin/{vin}")
    CarOnSale getCarByVin(@PathVariable("vin") String vin);

    @GetExchange("/getCarsByBrand/{brand}")
    List<CarOnSale> getCarsByBrand(@PathVariable("brand") String brand);

    @GetExchange("/getCarsWithPriceBiggerThan/{price}")
    List<CarOnSale> getCarsWithPriceBiggerThan(@PathVariable("price") BigDecimal price);

    @PostExchange("/addCarOnSale")
    CarOnSale addCarOnSale(AddCarOnSaleForm addCarOnSaleForm, Long ownerId);

    @DeleteExchange("/deleteCarFromSaleByVin")
    void deleteCarFromSaleByVin(@RequestBody CarOnSale carOnSale);
}
