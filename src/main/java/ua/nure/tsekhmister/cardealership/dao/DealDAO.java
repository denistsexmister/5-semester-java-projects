package ua.nure.tsekhmister.cardealership.dao;

import ua.nure.tsekhmister.cardealership.entity.CarOnSale;
import ua.nure.tsekhmister.cardealership.entity.Deal;
import ua.nure.tsekhmister.cardealership.entity.SoldCar;
import ua.nure.tsekhmister.cardealership.entity.User;
import ua.nure.tsekhmister.cardealership.observer.DealListener;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public interface DealDAO {
    Deal addDeal(User seller, User buyer, CarOnSale car) throws SQLException;
    List<Deal> getDeals() throws SQLException;
    List<Deal> getDealsWhereSeller(User user) throws SQLException;
    List<Deal> getDealsWhereBuyer(User user) throws SQLException;
    List<Deal> getDealsSinceDate(LocalDateTime localDateTime) throws SQLException;
    List<Deal> getDealsWhereCostIsBiggerThan(BigDecimal price) throws SQLException;
    SoldCar getSoldCarByDeal(Deal deal) throws SQLException;
    void addDealListener(DealListener dealListener);
}
