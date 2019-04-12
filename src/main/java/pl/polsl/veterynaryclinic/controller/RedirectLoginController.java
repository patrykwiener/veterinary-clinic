package pl.polsl.veterynaryclinic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RedirectLoginController {

    @PostMapping("/redirect_login")
    public String redirectLogin(Model model, @RequestParam String login, @RequestParam String password) {
        model.addAttribute("login", login);
        model.addAttribute("password", password);
        return "redirect_login";
    }
}
