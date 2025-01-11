package ua.nure.tsekhmister.controllers.client;

import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import ua.nure.tsekhmister.commons.entity.CarOnSale;
import ua.nure.tsekhmister.commons.entity.Deal;
import ua.nure.tsekhmister.commons.entity.User;

import java.util.List;
import java.util.Map;

@HttpExchange(url = "/deals")
public interface DealClient {
    @PostExchange("/addDeal")
    Deal addDeal(User seller, User buyer, CarOnSale car);

    @GetExchange("/getDealsWhereUserSeller")
    List<Deal> getDealsWhereUserSeller(User loggedUser);

    @GetExchange("/getDealsWhereUserBuyer")
    List<Deal> getDealsWhereUserBuyer(User loggedUser);

    @GetExchange("/getSellers")
    Map<Long, User> getSellers(List<Deal> dealsWhereUserBuyer);

    @GetExchange("/getBuyers")
    Map<Long, User> getBuyers(List<Deal> dealsWhereUserSeller);
}
