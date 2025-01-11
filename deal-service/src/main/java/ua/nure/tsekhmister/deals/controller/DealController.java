package ua.nure.tsekhmister.deals.controller;

import org.springframework.web.bind.annotation.*;
import ua.nure.tsekhmister.commons.entity.CarOnSale;
import ua.nure.tsekhmister.commons.entity.Deal;
import ua.nure.tsekhmister.commons.entity.SellerBuyerCarOnSale;
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
    public Deal addDeal(@RequestBody SellerBuyerCarOnSale sellerBuyerCarOnSale) throws SQLException {
        User seller = sellerBuyerCarOnSale.getSeller();
        User buyer = sellerBuyerCarOnSale.getBuyer();
        CarOnSale car = sellerBuyerCarOnSale.getCarOnSale();
        return dealService.addDeal(seller, buyer, car);
    }

    @GetMapping("/getDealsWhereUserSeller")
    public List<Deal> getDealsWhereUserSeller(@RequestBody User loggedUser) throws SQLException {
        return dealService.getDealsWhereUserSeller(loggedUser);
    }

    @GetMapping("/getDealsWhereUserBuyer")
    public List<Deal> getDealsWhereUserBuyer(@RequestBody User loggedUser) throws SQLException {
        return dealService.getDealsWhereUserBuyer(loggedUser);
    }

    @GetMapping("/getSellers")
    public Map<Long, User> getSellers(@RequestBody List<Deal> dealsWhereUserBuyer) throws SQLException {
        return dealService.getSellers(dealsWhereUserBuyer);
    }

    @GetMapping("/getBuyers")
    public Map<Long, User> getBuyers(@RequestBody List<Deal> dealsWhereUserSeller) throws SQLException {
        return dealService.getBuyers(dealsWhereUserSeller);
    }
}
