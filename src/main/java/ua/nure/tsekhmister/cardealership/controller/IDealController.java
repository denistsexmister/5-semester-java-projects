package ua.nure.tsekhmister.cardealership.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;

public interface IDealController {
    String getMyDealsPage(Model model, HttpServletRequest httpServletRequest);
}
