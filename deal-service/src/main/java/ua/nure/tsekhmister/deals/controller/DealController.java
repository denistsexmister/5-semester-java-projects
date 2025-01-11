package ua.nure.tsekhmister.deals.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.nure.tsekhmister.commons.entity.CarOnSale;
import ua.nure.tsekhmister.commons.entity.Deal;
import ua.nure.tsekhmister.commons.entity.User;
import ua.nure.tsekhmister.deals.service.DealService;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/deals")
public class DealController {
    private final DealService dealService;

    public DealController(DealService dealService) {
        this.dealService = dealService;
    }

    @PostMapping("/addDeal")
    public Deal addDeal(User seller, User buyer, CarOnSale car) throws SQLException {
        return dealService.addDeal(seller, buyer, car);
    }

    @GetMapping("/getDealsWhereUserSeller")
    public List<Deal> getDealsWhereUserSeller(User loggedUser) throws SQLException {
        return dealService.getDealsWhereUserSeller(loggedUser);
    }

    @GetMapping("/getDealsWhereUserBuyer")
    public List<Deal> getDealsWhereUserBuyer(User loggedUser) throws SQLException {
        return dealService.getDealsWhereUserBuyer(loggedUser);
    }

    @GetMapping("/getSellers")
    public Map<Long, User> getSellers(List<Deal> dealsWhereUserBuyer) throws SQLException {
        return dealService.getSellers(dealsWhereUserBuyer);
    }

    @GetMapping("/getBuyers")
    public Map<Long, User> getBuyers(List<Deal> dealsWhereUserSeller) throws SQLException {
        return dealService.getBuyers(dealsWhereUserSeller);
    }
}
