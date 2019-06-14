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
public class WorkerEditClientController {

    private User clientToEdit;
    public static final String PAGE_ADDRESS = "/worker/client/edit_client";

    @Autowired
    private WorkerAddDataValidator workerAddDataValidator;

    @Autowired
    private UserRepository userRepository;

    @InitBinder("newEditedUser")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(workerAddDataValidator);
    }

    @GetMapping(PAGE_ADDRESS)
    public String getEditPersonalData(@SessionAttribute("loggedUser") User loggedUser, @ModelAttribute("newEditedUser") User newEditedUser, Model model, HttpSession session) throws ServletRequestBindingException {
        if (!loggedUser.getUserType().equals(UserType.DOCTOR_ID)) {
            throw new ServletRequestBindingException("");
        }
        model.addAttribute("banner", Banner.getBanner(Banner.DOCTOR_BANNER, WorkerChooseClientController.PAGE_ADDRESS));
        clientToEdit = (User) session.getAttribute("client");
        session.removeAttribute("client");
        model.addAttribute("newEditedUser", clientToEdit);
        return PAGE_ADDRESS;
    }

    @PostMapping(PAGE_ADDRESS)
    public String postEditPersonalData(@SessionAttribute("loggedUser") User loggedUser, @ModelAttribute("newEditedUser") User newEditedUser, BindingResult bindingResult, Model model, HttpSession session) {
        session.setAttribute("previousUser", clientToEdit);
        workerAddDataValidator.validate(newEditedUser, bindingResult);
        session.removeAttribute("previousUser");
        if (bindingResult.hasErrors()) {
            model.addAttribute("banner", Banner.getBanner(Banner.DOCTOR_BANNER, WorkerChooseClientController.PAGE_ADDRESS));
            return PAGE_ADDRESS;
        }
        newEditedUser.setUserType(clientToEdit.getUserType());
        userRepository.save(newEditedUser);
        return "redirect:" + WorkerChooseClientController.PAGE_ADDRESS;
    }

}
