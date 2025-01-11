package ua.nure.tsekhmister.controllers.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.nure.tsekhmister.controllers.client.DealClient;
import ua.nure.tsekhmister.commons.entity.Deal;
import ua.nure.tsekhmister.commons.entity.User;

import java.util.List;
import java.util.Map;

@Controller
public class DealController {
    private final DealClient dealClient;

    @Autowired
    public DealController(DealClient dealClient) {
        this.dealClient = dealClient;
    }

    @GetMapping(path = "/myDeals")
    public String getMyDealsPage(Model model, HttpServletRequest httpServletRequest) {
        User loggedUser = (User) httpServletRequest.getSession().getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/";
        }

        List<Deal> dealsWhereUserSeller = dealClient.getDealsWhereUserSeller(loggedUser);
        List<Deal> dealsWhereUserBuyer = dealClient.getDealsWhereUserBuyer(loggedUser);
        Map<Long, User> sellers = dealClient.getSellers(dealsWhereUserBuyer);
        Map<Long, User> buyers = dealClient.getBuyers(dealsWhereUserSeller);

        model.addAttribute("dealsWhereUserBuyer", dealsWhereUserBuyer);
        model.addAttribute("sellers", sellers);
        model.addAttribute("dealsWhereUserSeller", dealsWhereUserSeller);
        model.addAttribute("buyers", buyers);
        return "userPages/myDealsPage";
    }
}
