package pl.polsl.veterynaryclinic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.polsl.veterynaryclinic.constants.Banner;
import pl.polsl.veterynaryclinic.constants.UserType;
import pl.polsl.veterynaryclinic.model.User;
import pl.polsl.veterynaryclinic.repository.UserRepository;
import pl.polsl.veterynaryclinic.validator.UserRegistrationValidator;

@Controller
public class RegisterClientController {

    private static final String PAGE_ADDRESS = "/register_client";

    private static final String PAGE_SOURCE = "register_client";

    @Autowired
    private UserRegistrationValidator userRegistrationValidator;

    @Autowired
    private UserRepository userRepository;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(userRegistrationValidator);
    }

    @PostMapping(PAGE_ADDRESS)
    public String postRegister(@ModelAttribute("user") @Validated User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("banner", Banner.getBanner(Banner.NOT_LOGGED_BANNER, PAGE_ADDRESS));
            return PAGE_SOURCE;
        }
        user.setUserType(UserType.CLIENT_ID);
        userRepository.save(user);
        return "redirect:" + HomeController.PAGE_ADDRESS;
    }

    @GetMapping(PAGE_ADDRESS)
    public String getRegister(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("banner", Banner.getBanner(Banner.NOT_LOGGED_BANNER, PAGE_ADDRESS));
        return PAGE_SOURCE;
    }
}
