package ua.nure.tsekhmister.cardealership.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyErrorController implements ErrorController {
    @RequestMapping(path = "/error")
    public String handleError() {
        return "errorPage";
    }

}
