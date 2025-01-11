package ua.nure.tsekhmister.commons.entity;

import java.math.BigDecimal;
import java.time.Year;
import java.util.Objects;

public class Car {
    protected String vin;
    protected String brand;
    protected Year productionYear;
    protected BigDecimal enginePower;

    public Car() {
    }

    public Car(String vin, String brand, Year productionYear, BigDecimal enginePower) {
        this.vin = vin;
        this.brand = brand;
        this.productionYear = productionYear;
        this.enginePower = enginePower;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Year getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(Year productionYear) {
        this.productionYear = productionYear;
    }

    public BigDecimal getEnginePower() {
        return enginePower;
    }

    public void setEnginePower(BigDecimal enginePower) {
        this.enginePower = enginePower;
    }

    @Override
    public String toString() {
        return "Car{" +
                "vin='" + vin + '\'' +
                ", brand='" + brand + '\'' +
                ", productionYear=" + productionYear +
                ", enginePower=" + enginePower +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(vin, car.vin) && Objects.equals(brand, car.brand) && Objects.equals(productionYear, car.productionYear) && Objects.equals(enginePower, car.enginePower);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vin, brand, productionYear, enginePower);
    }
}
