package pl.polsl.veterynaryclinic.controller.client.pets;

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
import pl.polsl.veterynaryclinic.model.Patient;
import pl.polsl.veterynaryclinic.model.User;
import pl.polsl.veterynaryclinic.repository.PatientRepository;
import pl.polsl.veterynaryclinic.validator.client.PatientAdditionValidator;

import javax.servlet.http.HttpSession;

@Controller
public class ClientAddPetController {

    static final String PAGE_ADDRESS = "/client/pets/add_pet";

    private static final String PAGE_SOURCE = "/client/pets/add_pet";

    @Autowired
    private PatientAdditionValidator patientAdditionValidator;

    @Autowired
    PatientRepository patientRepository;

    @InitBinder("patient")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(patientAdditionValidator);
    }

    @PostMapping(PAGE_ADDRESS)
    public String postAddPet(@ModelAttribute("patient") @Validated Patient patient, BindingResult bindingResult, Model model, HttpSession session, @RequestParam String action) {
        if (action.equals("save")) {
            if (bindingResult.hasErrors()) {
                model.addAttribute("banner", Banner.getBanner(Banner.CLIENT_BANNER, ClientPetsController.PAGE_ADDRESS));
                return PAGE_SOURCE;
            }
            User loggedUser = (User) session.getAttribute("loggedUser");
            patient.setOwner(loggedUser);
            patientRepository.save(patient);
        }
        return "redirect:/client/pets/pets";
    }

    @GetMapping(PAGE_ADDRESS)
    public String getAddPet(@ModelAttribute("patient") Patient patient, @SessionAttribute("loggedUser") User loggedUser, Model model) throws ServletRequestBindingException {
        if (!loggedUser.getUserType().equals(UserType.CLIENT_ID))
            throw new ServletRequestBindingException("");
        model.addAttribute("banner", Banner.getBanner(Banner.CLIENT_BANNER, ClientPetsController.PAGE_ADDRESS));
        return PAGE_SOURCE;
    }
}
