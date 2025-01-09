package ua.nure.tsekhmister.cardealership.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

@Controller
public class CarController {
    private final CarService carService;
    private final UserService userService;
    private final DealService dealService;

    @Autowired
    public CarController(CarService carService, UserService userService, DealService dealService) {
        this.carService = carService;
        this.userService = userService;
        this.dealService = dealService;
    }

    @GetMapping(path = "/")
    public String getMainPageView(Model model) {
        try {
            model.addAttribute("carsOnSale", carService.getCars());

            return "mainPage";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(path = "/car")
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

    @GetMapping(path = "/searchByBrand")
    public String getSearchByBrandPage(Model model) {
        model.addAttribute("searchForm", new SearchByBrandForm());
        return "searchPages/searchByBrandPage";
    }

    @PostMapping(path = "/searchByBrand")
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

    @GetMapping(path = "/searchByPrice")
    public String getSearchByPricePage(Model model) {
        model.addAttribute("searchForm", new SearchByPriceForm());
        return "searchPages/searchByPricePage";
    }

    @PostMapping(path = "/searchByPrice")
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

    @GetMapping(path = "/addCarOnSale")
    public String getAddCarOnSalePage(Model model, HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getSession().getAttribute("loggedUser") == null) {
            return "redirect:/";
        }
        model.addAttribute("addCarOnSaleForm", new AddCarOnSaleForm());
        return "carPages/addCarOnSalePage";
    }

    @PostMapping(path = "/addCarOnSale")
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

    @GetMapping(path = "/deleteCarFromSale")
    public String getDeleteCarFromSalePage(Model model, HttpServletRequest httpServletRequest) {
        User loggedUser = (User) httpServletRequest.getSession().getAttribute("loggedUser");
        String vin = httpServletRequest.getParameter("vin");

        try {
            CarOnSale car = carService.getCarByVin(vin);
            if (loggedUser == null || car == null) {
                return "redirect:/";
            }
            Long userIdByCar = car.getOwnerId();

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

    @PostMapping(path = "/deleteCarFromSale")
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

    @GetMapping(path = "/buyCar")
    public String getBuyCarPage(Model model, HttpServletRequest httpServletRequest) {
        String vin = httpServletRequest.getParameter("vin");
        if (vin == null) {
            return "redirect:/";
        }

        try {
            CarOnSale car = carService.getCarByVin(vin);
            User loggedUser = (User) httpServletRequest.getSession().getAttribute("loggedUser");
            if (car == null || (loggedUser != null && Objects.equals(loggedUser.getId(), car.getOwnerId()))) {
                return "redirect:/";
            }

            model.addAttribute("areYouSureForm", new AreYouSureForm());
            httpServletRequest.getSession().setAttribute("car", car);
            return "carPages/buyCarPage";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping(path = "/buyCar")
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
