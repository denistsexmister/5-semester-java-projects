package ua.nure.tsekhmister.cardealership.form;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.Year;

public class AddCarOnSaleForm {
    @Valid
    @NotBlank(message = "You must write correct vin code!")
    @Size(max = 17, min = 17, message = "Vin code must be 17 characters!")
    private String vin;
    @Valid
    @NotBlank(message = "You must write some brand!")
    @Size(max = 50, message = "Brand name must be less than 50 characters!")
    private String brand;
    @Valid
    @NotNull(message = "You must write some year!")
//    @Min(value = 1900, message = "You can't choose year before 1900!")
//    @Max(value = 2100, message = "You can't choose year after 2100!")
    private Year productionYear;
    @Valid
    @NotNull(message = "You must write some engine power!")
    @Min(value = 1, message = "Engine power must be positive!")
    @Max(value = 1000, message = "Engine power can't be bigger than 1000!")
    private BigDecimal enginePower;
    @Valid
    @NotNull(message = "You must write some price!")
    @Min(value = 1, message = "Price must be positive1")
    @Max(value = 1000000000, message = "Price can't be bigger than 1000000000!")
    private BigDecimal price;

    public AddCarOnSaleForm() {
    }

    public AddCarOnSaleForm(String vin, String brand, Year productionYear, BigDecimal enginePower, BigDecimal price) {
        this.vin = vin;
        this.brand = brand;
        this.productionYear = productionYear;
        this.enginePower = enginePower;
        this.price = price;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
