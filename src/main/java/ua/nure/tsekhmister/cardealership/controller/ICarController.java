package ua.nure.tsekhmister.cardealership.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.nure.tsekhmister.cardealership.form.AddCarOnSaleForm;
import ua.nure.tsekhmister.cardealership.form.AreYouSureForm;
import ua.nure.tsekhmister.cardealership.form.search.SearchByBrandForm;
import ua.nure.tsekhmister.cardealership.form.search.SearchByPriceForm;

public interface ICarController {
    String getMainPageView(Model model);

    String getCarPage(Model model, HttpServletRequest httpServletRequest);

    String getSearchByBrandPage(Model model);

    String findCarByBrand(Model model, @Validated SearchByBrandForm searchByBrandForm,
                          BindingResult bindingResult);

    String getSearchByPricePage(Model model);

    String findCarByPrice(Model model, @Validated SearchByPriceForm searchByPriceForm,
                          BindingResult bindingResult);

    String getAddCarOnSalePage(Model model, HttpServletRequest httpServletRequest);

    String addCarOnSale(Model model, HttpServletRequest httpServletRequest,
                        @Validated AddCarOnSaleForm addCarOnSaleForm,
                        BindingResult bindingResult, RedirectAttributes redirectAttributes);

    String getDeleteCarFromSalePage(Model model, HttpServletRequest httpServletRequest);

    String deleteCarFromSale(Model model, AreYouSureForm areYouSureForm,
                             HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes);

    String getBuyCarPage(Model model, HttpServletRequest httpServletRequest);

    String buyCar(Model model, AreYouSureForm areYouSureForm,
                  HttpServletRequest httpServletRequest);
}
