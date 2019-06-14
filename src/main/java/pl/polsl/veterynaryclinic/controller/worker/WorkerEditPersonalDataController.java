package pl.polsl.veterynaryclinic.controller.worker;

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
public class WorkerEditPersonalDataController {

    public static final String PAGE_ADDRESS = "/worker/edit_personal_data";

    @Autowired
    private WorkerAddDataValidator workerAddDataValidator;

    @Autowired
    private UserRepository userRepository;

    @InitBinder("newEditedDoctor")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(workerAddDataValidator);
    }

    @GetMapping(PAGE_ADDRESS)
    public String getEditPersonalData(@SessionAttribute("loggedUser") User loggedUser, @ModelAttribute("newEditedDoctor") User newEditedDoctor, Model model) throws ServletRequestBindingException {
        if (!loggedUser.getUserType().equals(UserType.DOCTOR_ID)) {
            throw new ServletRequestBindingException("");
        }
        model.addAttribute("banner", Banner.getBanner(Banner.DOCTOR_BANNER, PAGE_ADDRESS));
        model.addAttribute("newEditedDoctor", loggedUser);
        return PAGE_ADDRESS;
    }

    @PostMapping(PAGE_ADDRESS)
    public String postEditPersonalData(@SessionAttribute("loggedUser") User loggedUser, @ModelAttribute("newEditedDoctor") User newEditedDoctor, BindingResult bindingResult, Model model, HttpSession session) {
        session.setAttribute("previousUser", loggedUser);
        workerAddDataValidator.validate(newEditedDoctor, bindingResult);
        session.removeAttribute("previousUser");
        if (bindingResult.hasErrors()) {
            model.addAttribute("banner", Banner.getBanner(Banner.DOCTOR_BANNER, PAGE_ADDRESS));
            return PAGE_ADDRESS;
        }
        newEditedDoctor.setUserType(loggedUser.getUserType());
        userRepository.save(newEditedDoctor);
        session.setAttribute("loggedUser", userRepository.findByLogin(newEditedDoctor.getLogin()));
        return "redirect:" + PAGE_ADDRESS;
    }
}
