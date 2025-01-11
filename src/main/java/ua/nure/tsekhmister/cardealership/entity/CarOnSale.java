package ua.nure.tsekhmister.cardealership.entity;

import java.math.BigDecimal;
import java.time.Year;

public class CarOnSale extends Car implements Cloneable{
    private Long ownerId;
    private BigDecimal price;
    public CarOnSale() {
        super();
    }
    public CarOnSale(String vin, Long ownerId, String brand, Year productionYear,
                     BigDecimal enginePower, BigDecimal price) {
        super(vin, brand, productionYear, enginePower);
        this.price = price;
        this.ownerId = ownerId;
    }

    public static Builder builder(String vin, String brand, Year productionYear, BigDecimal enginePower,
                                  Long ownerId, BigDecimal price) {
        return new Builder(vin, brand, productionYear, enginePower, ownerId, price);
    }

    public static class Builder extends Car.Builder {
        private final Long ownerId;
        private final BigDecimal price;

        public Builder(String vin, String brand, Year productionYear, BigDecimal enginePower,
                       Long ownerId, BigDecimal price) {
            super(vin, brand, productionYear, enginePower);
            this.ownerId = ownerId;
            this.price = price;
        }

        public CarOnSale build() {
            return new CarOnSale(vin, ownerId, brand, productionYear, enginePower, price);
        }
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "CarOnSale{" +
                "vin='" + vin + '\'' +
                ", ownerId=" + ownerId +
                ", brand='" + brand + '\'' +
                ", productionYear=" + productionYear +
                ", enginePower=" + enginePower +
                ", price=" + price +
                '}';
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
