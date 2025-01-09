package ua.nure.tsekhmister.cardealership.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Deal {
    private Long dealId;
    private Long sellerId;
    private Long buyerId;
    private LocalDateTime dealDate;
    private BigDecimal cost;

    public Deal() {

    }
    public Deal(Long sellerId, Long buyerId, LocalDateTime dealDate, BigDecimal cost) {
        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.dealDate = dealDate;
        this.cost = cost;
    }

    public Long getDealId() {
        return dealId;
    }

    public void setDealId(Long dealId) {
        this.dealId = dealId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public LocalDateTime getDealDate() {
        return dealDate;
    }

    public void setDealDate(LocalDateTime dealDate) {
        this.dealDate = dealDate;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Deal{" +
                "dealId=" + dealId +
                ", sellerId=" + sellerId +
                ", buyerId=" + buyerId +
                ", dealDate=" + dealDate +
                ", cost=" + cost +
                '}';
    }
}
