package ua.nure.tsekhmister.cardealership.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.nure.tsekhmister.cardealership.entity.User;
import ua.nure.tsekhmister.cardealership.form.AddCarOnSaleForm;
import ua.nure.tsekhmister.cardealership.form.AreYouSureForm;
import ua.nure.tsekhmister.cardealership.form.search.SearchByBrandForm;
import ua.nure.tsekhmister.cardealership.form.search.SearchByPriceForm;

@Controller
public class CarControllerProxy implements ICarController {
    private final ICarController carController;

    public CarControllerProxy(ICarController carController) {
        this.carController = carController;
    }

    @Override
    @GetMapping(path = "/")
    public String getMainPageView(Model model) {
        return carController.getMainPageView(model);
    }

    @Override
    @GetMapping(path = "/car")
    public String getCarPage(Model model, HttpServletRequest httpServletRequest) {
        return carController.getCarPage(model, httpServletRequest);
    }

    @Override
    @GetMapping(path = "/searchByBrand")
    public String getSearchByBrandPage(Model model) {
        return carController.getSearchByBrandPage(model);
    }

    @Override
    @PostMapping(path = "/searchByBrand")
    public String findCarByBrand(Model model, @Validated SearchByBrandForm searchByBrandForm,
                                 BindingResult bindingResult) {
        return carController.findCarByBrand(model, searchByBrandForm, bindingResult);
    }

    @Override
    @GetMapping(path = "/searchByPrice")
    public String getSearchByPricePage(Model model) {
        return carController.getSearchByPricePage(model);
    }

    @Override
    @PostMapping(path = "/searchByPrice")
    public String findCarByPrice(Model model, @Validated SearchByPriceForm searchByPriceForm,
                                 BindingResult bindingResult) {
        return carController.findCarByPrice(model, searchByPriceForm, bindingResult);
    }

    @Override
    @GetMapping(path = "/addCarOnSale")
    public String getAddCarOnSalePage(Model model, HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getSession().getAttribute("loggedUser") == null) {
            return "redirect:/";
        }
        return carController.getAddCarOnSalePage(model, httpServletRequest);
    }

    @Override
    @PostMapping(path = "/addCarOnSale")
    public String addCarOnSale(Model model, HttpServletRequest httpServletRequest,
                               @Validated AddCarOnSaleForm addCarOnSaleForm,
                               BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        return carController.addCarOnSale(model, httpServletRequest, addCarOnSaleForm, bindingResult, redirectAttributes);
    }

    @Override
    @GetMapping(path = "/deleteCarFromSale")
    public String getDeleteCarFromSalePage(Model model, HttpServletRequest httpServletRequest) {
        User loggedUser = (User) httpServletRequest.getSession().getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/";
        }

        return carController.getDeleteCarFromSalePage(model, httpServletRequest);
    }

    @Override
    @PostMapping(path = "/deleteCarFromSale")
    public String deleteCarFromSale(Model model, AreYouSureForm areYouSureForm,
                                    HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {
        return carController.deleteCarFromSale(model, areYouSureForm, httpServletRequest, redirectAttributes);
    }

    @Override
    @GetMapping(path = "/buyCar")
    public String getBuyCarPage(Model model, HttpServletRequest httpServletRequest) {
        User loggedUser = (User) httpServletRequest.getSession().getAttribute("loggedUser");
        if (loggedUser != null) {
            return "redirect:/";
        }
        return carController.getBuyCarPage(model, httpServletRequest);
    }

    @Override
    @PostMapping(path = "/buyCar")
    public String buyCar(Model model, AreYouSureForm areYouSureForm,
                         HttpServletRequest httpServletRequest) {
        return carController.buyCar(model, areYouSureForm, httpServletRequest);
    }
}
