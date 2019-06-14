package pl.polsl.veterynaryclinic.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorHandlerController implements ErrorController {

    @GetMapping("/error")
    public String getError() {
        return "redirect:" + HomeController.PAGE_ADDRESS;
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
