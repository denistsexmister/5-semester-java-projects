package ua.nure.tsekhmister.controllers.client;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import ua.nure.tsekhmister.commons.entity.CarOnSale;
import ua.nure.tsekhmister.commons.entity.Deal;
import ua.nure.tsekhmister.commons.entity.SellerBuyerCarOnSale;
import ua.nure.tsekhmister.commons.entity.User;

import java.util.List;
import java.util.Map;

@HttpExchange(url = "/deals")
public interface DealClient {
    @PostExchange("/addDeal")
    Deal addDeal(@RequestBody SellerBuyerCarOnSale sellerBuyerCarOnSale);

    @GetExchange("/getDealsWhereUserSeller")
    List<Deal> getDealsWhereUserSeller(@RequestBody User loggedUser);

    @GetExchange("/getDealsWhereUserBuyer")
    List<Deal> getDealsWhereUserBuyer(@RequestBody User loggedUser);

    @GetExchange("/getSellers")
    Map<Long, User> getSellers(@RequestBody List<Deal> dealsWhereUserBuyer);

    @GetExchange("/getBuyers")
    Map<Long, User> getBuyers(@RequestBody List<Deal> dealsWhereUserSeller);
}
