package ua.nure.tsekhmister.deals.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nure.tsekhmister.commons.entity.CarOnSale;
import ua.nure.tsekhmister.commons.entity.Deal;
import ua.nure.tsekhmister.commons.entity.User;
import ua.nure.tsekhmister.deals.dao.DAOFactory;
import ua.nure.tsekhmister.deals.dao.DealDAO;
import ua.nure.tsekhmister.deals.dao.UserDAO;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DealService {
    private final DealDAO dealDAO;
    private final UserDAO userDAO;


    @Autowired
    public DealService(DAOFactory daoFactory) {
        this.dealDAO = daoFactory.getDealDAO();
        this.userDAO = daoFactory.getUserDAO();
    }

    public Deal addDeal(User seller, User buyer, CarOnSale car) throws SQLException {
        return dealDAO.addDeal(seller, buyer, car);
    }

    public List<Deal> getDealsWhereUserSeller(User loggedUser) throws SQLException {
        return dealDAO.getDealsWhereSeller(loggedUser);
    }

    public List<Deal> getDealsWhereUserBuyer(User loggedUser) throws SQLException {
        return dealDAO.getDealsWhereBuyer(loggedUser);
    }

    public Map<Long, User> getSellers(List<Deal> dealsWhereUserBuyer) throws SQLException {
        Map<Long, User> sellers = new HashMap<>();
        for (Deal deal : dealsWhereUserBuyer) {
            sellers.put(deal.getSellerId(), userDAO.getUserById(deal.getSellerId()));
        }

        return sellers;
    }

    public Map<Long, User> getBuyers(List<Deal> dealsWhereUserSeller) throws SQLException {
        Map<Long, User> buyers = new HashMap<>();
        for (Deal deal : dealsWhereUserSeller) {
            buyers.put(deal.getBuyerId(), userDAO.getUserById(deal.getBuyerId()));
        }

        return buyers;
    }
}
