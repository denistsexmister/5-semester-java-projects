package ua.nure.tsekhmister.cardealership.dao.collection;

import ua.nure.tsekhmister.cardealership.dao.CarDAO;
import ua.nure.tsekhmister.cardealership.dao.DealDAO;
import ua.nure.tsekhmister.cardealership.entity.CarOnSale;
import ua.nure.tsekhmister.cardealership.entity.Deal;
import ua.nure.tsekhmister.cardealership.entity.SoldCar;
import ua.nure.tsekhmister.cardealership.entity.User;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class ListDealDAO implements DealDAO {
    private static final List<Deal> deals = new ArrayList<>();
    private static final List<SoldCar> soldCars = new ArrayList<>();
    private static CarDAO carDAO;
    private static final AtomicLong dealIdCounter = new AtomicLong(1L);

    public ListDealDAO(CarDAO carDAOPassed) {
        carDAO = carDAOPassed;
    }

    @Override
    public Deal addDeal(User seller, User buyer, CarOnSale car) throws SQLException {
        if (Objects.equals(seller.getId(), buyer.getId())) {
            throw new SQLException("Seller and buyer id can't be equal");
        }
        carDAO.deleteCarFromSaleByVin(car);

        Deal deal = new Deal();

        deal.setDealId(dealIdCounter.getAndIncrement());
        deal.setSellerId(seller.getId());
        deal.setBuyerId(buyer.getId());
        deal.setCost(car.getPrice());
        deal.setDealDate(LocalDateTime.now());

        deals.add(deal);

        SoldCar soldCar = new SoldCar(car.getVin(), car.getBrand(),
                car.getProductionYear(), car.getEnginePower());
        soldCar.setDealId(deal.getDealId());
        soldCars.add(soldCar);

        return deal;
    }

    @Override
    public List<Deal> getDeals() throws SQLException {
        return deals;
    }

    @Override
    public List<Deal> getDealsWhereSeller(User user) throws SQLException {
        List<Deal> result = new ArrayList<>();
        for (Deal deal : deals) {
            if (Objects.equals(deal.getSellerId(), user.getId())) {
                result.add(deal);
            }
        }

        return result;
    }

    @Override
    public List<Deal> getDealsWhereBuyer(User user) throws SQLException {
        List<Deal> result = new ArrayList<>();
        for (Deal deal : deals) {
            if (Objects.equals(deal.getBuyerId(), user.getId())) {
                result.add(deal);
            }
        }

        return result;
    }

    @Override
    public List<Deal> getDealsSinceDate(LocalDateTime localDateTime) throws SQLException {
        List<Deal> result = new ArrayList<>();
        for (Deal deal : deals) {
            if (deal.getDealDate() != null && deal.getDealDate().isAfter(localDateTime)) {
                result.add(deal);
            }
        }

        return result;
    }

    @Override
    public List<Deal> getDealsWhereCostIsBiggerThan(BigDecimal price) throws SQLException {
        List<Deal> result = new ArrayList<>();
        for (Deal deal : deals) {
            if (deal.getCost() != null && deal.getCost().compareTo(price) > 0) {
                result.add(deal);
            }
        }

        return result;
    }

    @Override
    public SoldCar getSoldCarByDeal(Deal deal) throws SQLException {
        for (SoldCar car : soldCars) {
            if (Objects.equals(car.getDealId(), deal.getDealId())) {
                return car;
            }
        }

        return null;
    }
}
