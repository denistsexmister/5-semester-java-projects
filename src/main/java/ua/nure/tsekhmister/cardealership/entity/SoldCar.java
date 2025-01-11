package ua.nure.tsekhmister.cardealership.entity;

import java.math.BigDecimal;
import java.time.Year;

public class SoldCar extends Car{
    private Long dealId;

    public SoldCar() {
        super();
    }

    public SoldCar(String vin, Long dealId, String brand, Year productionYear, BigDecimal enginePower) {
        super(vin, brand, productionYear, enginePower);
        this.dealId = dealId;
    }

    public SoldCar(String vin, String brand, Year productionYear, BigDecimal enginePower) {
        super(vin, brand, productionYear, enginePower);
    }


    public static Builder builder(String vin, String brand, Year productionYear, BigDecimal enginePower,
                                  Long dealId) {
        return new Builder(vin, brand, productionYear, enginePower, dealId);
    }

    public static class Builder extends Car.Builder {
        private final Long dealId;

        public Builder(String vin, String brand, Year productionYear, BigDecimal enginePower,
                       Long dealId) {
            super(vin, brand, productionYear, enginePower);
            this.dealId = dealId;
        }

        public SoldCar build() {
            return new SoldCar(vin, dealId, brand, productionYear, enginePower);
        }
    }

    public Long getDealId() {
        return dealId;
    }

    public void setDealId(Long dealId) {
        this.dealId = dealId;
    }

    @Override
    public String toString() {
        return "SoldCar{" +
                "dealId=" + dealId +
                ", vin='" + vin + '\'' +
                ", brand='" + brand + '\'' +
                ", productionYear=" + productionYear +
                ", enginePower=" + enginePower +
                '}';
    }
}
