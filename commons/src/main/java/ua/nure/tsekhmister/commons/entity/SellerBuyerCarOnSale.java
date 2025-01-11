package ua.nure.tsekhmister.commons.entity;

public class SellerBuyerCarOnSale {
    private User seller;
    private User buyer;
    private CarOnSale carOnSale;

    public SellerBuyerCarOnSale() {

    }

    public SellerBuyerCarOnSale(User seller, User buyer, CarOnSale carOnSale) {
        this.seller = seller;
        this.buyer = buyer;
        this.carOnSale = carOnSale;
    }


    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public CarOnSale getCarOnSale() {
        return carOnSale;
    }

    public void setCarOnSale(CarOnSale carOnSale) {
        this.carOnSale = carOnSale;
    }
}
