package ua.nure.tsekhmister.cardealership.entity;

import java.math.BigDecimal;
import java.time.Year;

public class SoldCar extends Car{
    private Long dealId;

    public SoldCar() {
        super();
    }

    public SoldCar(String vin, String brand, Year productionYear, BigDecimal enginePower) {
        super(vin, brand, productionYear, enginePower);
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
