package ua.nure.tsekhmister.cardealership.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.nure.tsekhmister.cardealership.entity.Deal;
import ua.nure.tsekhmister.cardealership.entity.User;
import ua.nure.tsekhmister.cardealership.service.DealService;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Controller
public class DealController {
    private final DealService dealService;

    @Autowired
    public DealController(DealService dealService) {
        this.dealService = dealService;
    }

    @GetMapping(path = "/myDeals")
    public String getMyDealsPage(Model model, HttpServletRequest httpServletRequest) {
        User loggedUser = (User) httpServletRequest.getSession().getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/";
        }

        try {
            List<Deal> dealsWhereUserSeller = dealService.getDealsWhereUserSeller(loggedUser);
            List<Deal> dealsWhereUserBuyer = dealService.getDealsWhereUserBuyer(loggedUser);
            Map<Long, User> sellers = dealService.getSellers(dealsWhereUserBuyer);
            Map<Long, User> buyers = dealService.getBuyers(dealsWhereUserSeller);

            model.addAttribute("dealsWhereUserBuyer", dealsWhereUserBuyer);
            model.addAttribute("sellers", sellers);
            model.addAttribute("dealsWhereUserSeller", dealsWhereUserSeller);
            model.addAttribute("buyers", buyers);
            return "userPages/myDealsPage";
        } catch(SQLException e) {
            return "redirect:/error";
        }
    }
}
