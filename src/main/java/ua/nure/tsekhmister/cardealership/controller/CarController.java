package ua.nure.tsekhmister.cardealership.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.nure.tsekhmister.cardealership.entity.CarOnSale;
import ua.nure.tsekhmister.cardealership.entity.User;
import ua.nure.tsekhmister.cardealership.form.AddCarOnSaleForm;
import ua.nure.tsekhmister.cardealership.form.AreYouSureForm;
import ua.nure.tsekhmister.cardealership.form.search.SearchByBrandForm;
import ua.nure.tsekhmister.cardealership.form.search.SearchByPriceForm;
import ua.nure.tsekhmister.cardealership.service.CarService;
import ua.nure.tsekhmister.cardealership.service.DealService;
import ua.nure.tsekhmister.cardealership.service.UserService;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Component
public class CarController implements ICarController {
    private final CarService carService;
    private final UserService userService;
    private final DealService dealService;

    @Autowired
    public CarController(CarService carService, UserService userService, DealService dealService) {
        this.carService = carService;
        this.userService = userService;
        this.dealService = dealService;
    }

    @Override
    public String getMainPageView(Model model) {
        try {
            model.addAttribute("carsOnSale", carService.getCars());

            return "mainPage";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getCarPage(Model model, HttpServletRequest httpServletRequest) {
        String vin = httpServletRequest.getParameter("vin");
        if (vin == null) {
            return "redirect:/";
        }

        try {
            CarOnSale car = carService.getCarByVin(vin);
            User user = userService.getUserById(car.getOwnerId());
            model.addAttribute("car", car);
            model.addAttribute("user", user);
            return "carPages/carPage";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getSearchByBrandPage(Model model) {
        model.addAttribute("searchForm", new SearchByBrandForm());
        return "searchPages/searchByBrandPage";
    }

    @Override
    public String findCarByBrand(Model model, @Validated SearchByBrandForm searchByBrandForm,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("searchForm", new SearchByBrandForm());
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "searchPages/searchByBrandPage";
        }

        try {
            List<CarOnSale> carsOnSale = carService
                    .getCarsByBrand(searchByBrandForm.getSearchQuery());

            model.addAttribute("carsOnSale", carsOnSale);
            model.addAttribute("searchForm", searchByBrandForm);
            return "searchPages/searchByBrandPage";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getSearchByPricePage(Model model) {
        model.addAttribute("searchForm", new SearchByPriceForm());
        return "searchPages/searchByPricePage";
    }

    @Override
    public String findCarByPrice(Model model, @Validated SearchByPriceForm searchByPriceForm,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("searchForm", new SearchByPriceForm());
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "searchPages/searchByPricePage";
        }

        try {
            List<CarOnSale> carsOnSale = carService
                    .getCarsWithPriceBiggerThan(searchByPriceForm.getSearchQuery());

            model.addAttribute("carsOnSale", carsOnSale);
            model.addAttribute("searchForm", searchByPriceForm);
            return "searchPages/searchByPricePage";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getAddCarOnSalePage(Model model, HttpServletRequest httpServletRequest) {
        model.addAttribute("addCarOnSaleForm", new AddCarOnSaleForm());
        return "carPages/addCarOnSalePage";
    }

    @Override
    public String addCarOnSale(Model model, HttpServletRequest httpServletRequest,
                               @Validated AddCarOnSaleForm addCarOnSaleForm,
                               BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("addCarOnSaleForm", addCarOnSaleForm);
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "carPages/addCarOnSalePage";
        }

        try {
            CarOnSale car = carService.addCarOnSale(addCarOnSaleForm,
                            ((User) httpServletRequest.getSession().
                                    getAttribute("loggedUser")).getId());

            redirectAttributes.addAttribute("vin", car.getVin());
            return "redirect:/car";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getDeleteCarFromSalePage(Model model, HttpServletRequest httpServletRequest) {
        String vin = httpServletRequest.getParameter("vin");

        try {
            CarOnSale car = carService.getCarByVin(vin);
            if (car == null) {
                return "redirect:/";
            }
            Long userIdByCar = car.getOwnerId();
            User loggedUser = (User) httpServletRequest.getSession().getAttribute("loggedUser");

            if (!Objects.equals(userIdByCar, loggedUser.getId())) {
                return "redirect:/";
            }

            model.addAttribute("areYouSureForm", new AreYouSureForm());
            model.addAttribute("car", car);
            httpServletRequest.getSession().setAttribute("car", car);

            return "deletingPages/deleteCarFromSalePage";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String deleteCarFromSale(Model model, AreYouSureForm areYouSureForm,
                                    HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {
        CarOnSale car = (CarOnSale) httpServletRequest.getSession().getAttribute("car");
        httpServletRequest.getSession().removeAttribute("car");
        try {
            if (!areYouSureForm.isSure()) {
                redirectAttributes.addAttribute("vin", car.getVin());
                return "redirect:/car";
            }

            carService.deleteCarFromSaleByVin(car);
            return "redirect:/";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBuyCarPage(Model model, HttpServletRequest httpServletRequest) {
        String vin = httpServletRequest.getParameter("vin");
        if (vin == null) {
            return "redirect:/";
        }

        try {
            CarOnSale car = carService.getCarByVin(vin);
            User loggedUser = (User) httpServletRequest.getSession().getAttribute("loggedUser");
            if (car == null || Objects.equals(loggedUser.getId(), car.getOwnerId())) {
                return "redirect:/";
            }

            model.addAttribute("areYouSureForm", new AreYouSureForm());
            httpServletRequest.getSession().setAttribute("car", car);
            return "carPages/buyCarPage";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String buyCar(Model model, AreYouSureForm areYouSureForm,
                         HttpServletRequest httpServletRequest) {
        if (!areYouSureForm.isSure()) {
            return "redirect:/";
        }

        try {
            CarOnSale car = (CarOnSale) httpServletRequest.getSession().getAttribute("car");
            User seller = userService.getUserById(car.getOwnerId());
            User buyer = (User) httpServletRequest.getSession().getAttribute("loggedUser");

            dealService.addDeal(seller, buyer, car);
            httpServletRequest.getSession().removeAttribute("car");
            return "redirect:/";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
