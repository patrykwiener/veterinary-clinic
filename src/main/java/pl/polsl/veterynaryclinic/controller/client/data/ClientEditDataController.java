package pl.polsl.veterynaryclinic.controller.client.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pl.polsl.veterynaryclinic.constants.Banner;
import pl.polsl.veterynaryclinic.constants.UserType;
import pl.polsl.veterynaryclinic.model.User;
import pl.polsl.veterynaryclinic.repository.UserRepository;
import pl.polsl.veterynaryclinic.validator.client.ClientEditDataValidator;

import javax.servlet.http.HttpSession;

@Controller
public class ClientEditDataController {

    private static final String PAGE_ADDRESS = "/client/data/edit_data";

    private static final String PAGE_SOURCE = "/client/data/edit_data";

    @Autowired
    private ClientEditDataValidator clientEditDataValidator;

    @Autowired
    private UserRepository userRepository;

    @InitBinder("newEditedUser")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(clientEditDataValidator);
    }

    @PostMapping(PAGE_ADDRESS)
    public  String postChangeData(@SessionAttribute("loggedUser") User loggedUser, @ModelAttribute("newEditedUser") @Validated User newEditedUser, BindingResult bindingResult, @RequestParam String action, Model model, HttpSession session) {
        if (action.equals("save")) {
            if (bindingResult.hasErrors()) {
                model.addAttribute("banner", Banner.getBanner(Banner.CLIENT_BANNER, ClientDataController.PAGE_ADDRESS));
                return PAGE_SOURCE;
            }
            newEditedUser.setUserType(loggedUser.getUserType());
            userRepository.save(newEditedUser);
            session.setAttribute("loggedUser", userRepository.findByLogin(newEditedUser.getLogin()));
        }
        return "redirect:/client/data/data";
    }

    @GetMapping(PAGE_ADDRESS)
    public String getChangeData(@SessionAttribute("loggedUser")User loggedUser, Model model) throws ServletRequestBindingException {
        if (!loggedUser.getUserType().equals(UserType.CLIENT_ID))
            throw new ServletRequestBindingException("");

        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("newEditedUser", loggedUser);
        model.addAttribute("banner", Banner.getBanner(Banner.CLIENT_BANNER, ClientDataController.PAGE_ADDRESS));
        return PAGE_SOURCE;
    }
}
