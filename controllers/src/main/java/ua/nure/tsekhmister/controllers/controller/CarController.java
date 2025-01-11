package ua.nure.tsekhmister.controllers.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.nure.tsekhmister.controllers.client.CarClient;
import ua.nure.tsekhmister.controllers.client.DealClient;
import ua.nure.tsekhmister.controllers.client.UserClient;
import ua.nure.tsekhmister.commons.entity.CarOnSale;
import ua.nure.tsekhmister.commons.entity.User;
import ua.nure.tsekhmister.commons.form.AddCarOnSaleForm;
import ua.nure.tsekhmister.commons.form.AreYouSureForm;
import ua.nure.tsekhmister.commons.form.search.SearchByBrandForm;
import ua.nure.tsekhmister.commons.form.search.SearchByPriceForm;

import java.util.List;
import java.util.Objects;

@Controller
public class CarController {
    private final CarClient carClient;
    private final UserClient userClient;
    private final DealClient dealClient;

    @Autowired
    public CarController(CarClient carClient, UserClient userClient, DealClient dealClient) {
        this.carClient = carClient;
        this.userClient = userClient;
        this.dealClient = dealClient;
    }

    @GetMapping(path = "/")
    public String getMainPageView(Model model) {
        model.addAttribute("carsOnSale", carClient.getCars());

        return "mainPage";
    }

    @GetMapping(path = "/car")
    public String getCarPage(Model model, HttpServletRequest httpServletRequest) {
        String vin = httpServletRequest.getParameter("vin");
        if (vin == null) {
            return "redirect:/";
        }


        CarOnSale car = carClient.getCarByVin(vin);
        User user = userClient.getUserById(car.getOwnerId());
        model.addAttribute("car", car);
        model.addAttribute("user", user);
        return "carPages/carPage";

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


        List<CarOnSale> carsOnSale = carClient
                .getCarsByBrand(searchByBrandForm.getSearchQuery());

        model.addAttribute("carsOnSale", carsOnSale);
        model.addAttribute("searchForm", searchByBrandForm);
        return "searchPages/searchByBrandPage";

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


        List<CarOnSale> carsOnSale = carClient
                .getCarsWithPriceBiggerThan(searchByPriceForm.getSearchQuery());

        model.addAttribute("carsOnSale", carsOnSale);
        model.addAttribute("searchForm", searchByPriceForm);
        return "searchPages/searchByPricePage";
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


        CarOnSale car = carClient.addCarOnSale(addCarOnSaleForm,
                ((User) httpServletRequest.getSession().
                        getAttribute("loggedUser")).getId());

        redirectAttributes.addAttribute("vin", car.getVin());
        return "redirect:/car";
    }

    @GetMapping(path = "/deleteCarFromSale")
    public String getDeleteCarFromSalePage(Model model, HttpServletRequest httpServletRequest) {
        User loggedUser = (User) httpServletRequest.getSession().getAttribute("loggedUser");
        String vin = httpServletRequest.getParameter("vin");


        CarOnSale car = carClient.getCarByVin(vin);
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
    }

    @PostMapping(path = "/deleteCarFromSale")
    public String deleteCarFromSale(Model model, AreYouSureForm areYouSureForm,
                                    HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {
        CarOnSale car = (CarOnSale) httpServletRequest.getSession().getAttribute("car");
        httpServletRequest.getSession().removeAttribute("car");

        if (!areYouSureForm.isSure()) {
            redirectAttributes.addAttribute("vin", car.getVin());
            return "redirect:/car";
        }

        carClient.deleteCarFromSaleByVin(car);
        return "redirect:/";
    }

    @GetMapping(path = "/buyCar")
    public String getBuyCarPage(Model model, HttpServletRequest httpServletRequest) {
        String vin = httpServletRequest.getParameter("vin");
        if (vin == null) {
            return "redirect:/";
        }


        CarOnSale car = carClient.getCarByVin(vin);
        User loggedUser = (User) httpServletRequest.getSession().getAttribute("loggedUser");
        if (car == null || (loggedUser != null && Objects.equals(loggedUser.getId(), car.getOwnerId()))) {
            return "redirect:/";
        }

        model.addAttribute("areYouSureForm", new AreYouSureForm());
        httpServletRequest.getSession().setAttribute("car", car);
        return "carPages/buyCarPage";
    }

    @PostMapping(path = "/buyCar")
    public String buyCar(Model model, AreYouSureForm areYouSureForm,
                         HttpServletRequest httpServletRequest) {
        if (!areYouSureForm.isSure()) {
            return "redirect:/";
        }

        CarOnSale car = (CarOnSale) httpServletRequest.getSession().getAttribute("car");
        User seller = userClient.getUserById(car.getOwnerId());
        User buyer = (User) httpServletRequest.getSession().getAttribute("loggedUser");

        dealClient.addDeal(seller, buyer, car);
        httpServletRequest.getSession().removeAttribute("car");
        return "redirect:/";
    }
}
