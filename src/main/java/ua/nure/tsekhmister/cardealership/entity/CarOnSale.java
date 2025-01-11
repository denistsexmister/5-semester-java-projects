package ua.nure.tsekhmister.cardealership.entity;

import ua.nure.tsekhmister.cardealership.memento.CarOnSaleMemento;

import java.math.BigDecimal;
import java.time.Year;
import java.util.Stack;

public class CarOnSale extends Car implements Cloneable{
    private Long ownerId;
    private BigDecimal price;
    private final Stack<CarOnSaleMemento> history = new Stack<>();
    private boolean isDeleted = false;


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
        saveState();
        this.ownerId = ownerId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        saveState();
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

    public void delete() {
        isDeleted = true;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    private void saveState() {
        if (!isDeleted) {
            history.push(new CarOnSaleMemento(vin, ownerId, brand, productionYear, enginePower, price));
        }
    }

    public boolean undo() {
        if (!history.isEmpty() && !isDeleted) {
            CarOnSaleMemento memento = history.pop();
            this.vin = memento.getVin();
            this.ownerId = memento.getOwnerId();
            this.brand = memento.getBrand();
            this.productionYear = memento.getProductionYear();
            this.enginePower = memento.getEnginePower();
            this.price = memento.getPrice();
            return true;
        }
        return false;
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
