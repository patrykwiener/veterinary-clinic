package pl.polsl.veterynaryclinic.controller.worker.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pl.polsl.veterynaryclinic.constants.Banner;
import pl.polsl.veterynaryclinic.constants.UserType;
import pl.polsl.veterynaryclinic.model.User;
import pl.polsl.veterynaryclinic.repository.UserRepository;

@Controller
@SessionAttributes("client")
public class WorkerChooseClientController {

    public static final String PAGE_ADDRESS = "/worker/client/choose_client";

    @Autowired
    private UserRepository userRepository;

    @InitBinder("chosenClient")
    public void initBinder(WebDataBinder binder) {

    }

    @GetMapping(PAGE_ADDRESS)
    public String getChooseClient(@SessionAttribute("loggedUser") User loggedUser, @ModelAttribute("chosenClient") User chosenClient, Model model) throws ServletRequestBindingException {
        if (!loggedUser.getUserType().equals(UserType.DOCTOR_ID)) {
            throw new ServletRequestBindingException("");
        }
        model.addAttribute("banner", Banner.getBanner(Banner.DOCTOR_BANNER, PAGE_ADDRESS));
        model.addAttribute("clientList", userRepository.findAllByUserType(UserType.CLIENT_ID));
        return PAGE_ADDRESS;
    }

    @PostMapping(PAGE_ADDRESS)
    public String postChooseClient(@ModelAttribute("chosenClient") User chosenClient, Model model){
        chosenClient = userRepository.findByUserId(chosenClient.getUserId());
        model.addAttribute("client", chosenClient);
        return "redirect:" + WorkerEditClientController.PAGE_ADDRESS;
    }

}
