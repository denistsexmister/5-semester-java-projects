package ua.nure.tsekhmister.cardealership.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.nure.tsekhmister.cardealership.entity.User;

@Controller
public class DealControllerProxy implements IDealController {
    private final IDealController dealController;

    public DealControllerProxy(IDealController dealController) {
        this.dealController = dealController;
    }

    @Override
    @GetMapping(path = "/myDeals")
    public String getMyDealsPage(Model model, HttpServletRequest httpServletRequest) {
        User loggedUser = (User) httpServletRequest.getSession().getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/";
        }

        return dealController.getMyDealsPage(model, httpServletRequest);
    }
}
