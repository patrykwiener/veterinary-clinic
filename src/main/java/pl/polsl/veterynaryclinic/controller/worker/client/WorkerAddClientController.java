package pl.polsl.veterynaryclinic.controller.worker.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pl.polsl.veterynaryclinic.constants.Banner;
import pl.polsl.veterynaryclinic.constants.UserType;
import pl.polsl.veterynaryclinic.model.User;
import pl.polsl.veterynaryclinic.repository.UserRepository;
import pl.polsl.veterynaryclinic.validator.worker.WorkerAddDataValidator;

import javax.servlet.http.HttpSession;

@Controller
public class WorkerAddClientController {
    public static final String PAGE_ADDRESS = "/worker/client/add_client";

    @Autowired
    private WorkerAddDataValidator workerAddDataValidator;

    @Autowired
    private UserRepository userRepository;

    @InitBinder("newClient")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(workerAddDataValidator);
    }

    @GetMapping(PAGE_ADDRESS)
    public String getAddClient(@SessionAttribute("loggedUser") User loggedUser, @ModelAttribute("newClient") User newClient, Model model) throws ServletRequestBindingException {
        if (!loggedUser.getUserType().equals(UserType.DOCTOR_ID)) {
            throw new ServletRequestBindingException("");
        }
        model.addAttribute("banner", Banner.getBanner(Banner.DOCTOR_BANNER, PAGE_ADDRESS));
        return PAGE_ADDRESS;
    }

    @PostMapping(PAGE_ADDRESS)
    public String postEditPersonalData(@ModelAttribute("newClient") User newClient, BindingResult bindingResult, Model model, HttpSession session){
        session.removeAttribute("previousUser");
        workerAddDataValidator.validate(newClient, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("banner", Banner.getBanner(Banner.DOCTOR_BANNER, PAGE_ADDRESS));
            return PAGE_ADDRESS;
        }
        newClient.setUserType(UserType.CLIENT_ID);
        userRepository.save(newClient);
        return "redirect:" + PAGE_ADDRESS;
    }

}
