package ua.nure.tsekhmister.cardealership.memento;

import java.math.BigDecimal;
import java.time.Year;

public class CarOnSaleMemento {
    private final String vin;
    private final Long ownerId;
    private final String brand;
    private final Year productionYear;
    private final BigDecimal enginePower;
    private final BigDecimal price;

    public CarOnSaleMemento(String vin, Long ownerId, String brand, Year productionYear, BigDecimal enginePower, BigDecimal price) {
        this.vin = vin;
        this.ownerId = ownerId;
        this.brand = brand;
        this.productionYear = productionYear;
        this.enginePower = enginePower;
        this.price = price;
    }

    public String getVin() {
        return vin;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public String getBrand() {
        return brand;
    }

    public Year getProductionYear() {
        return productionYear;
    }

    public BigDecimal getEnginePower() {
        return enginePower;
    }

    public BigDecimal getPrice() {
        return price;
    }

}
