package pl.polsl.veterynaryclinic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LogoutController {

    @GetMapping("logout")
    public String getLogout(HttpSession session) {
        session.invalidate();
        return "redirect:" + HomeController.PAGE_ADDRESS;
    }
}
