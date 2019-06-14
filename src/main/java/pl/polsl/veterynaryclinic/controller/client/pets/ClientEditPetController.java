package pl.polsl.veterynaryclinic.controller.client.pets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
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
import pl.polsl.veterynaryclinic.validator.client.PatientEditionValidator;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class ClientEditPetController {

    static final String PAGE_ADDRESS = "/client/pets/edit_pet";

    private static final String PAGE_SOURCE = "/client/pets/edit_pet";

    @Autowired
    private PatientEditionValidator patientEditionValidator;

    @Autowired
    PatientRepository patientRepository;

    @InitBinder("newEditedPatient")
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.addValidators(patientEditionValidator);
    }

    @PostMapping(PAGE_ADDRESS)
    public String postEditPet(@SessionAttribute("editedPatient") Patient editedPatient, @ModelAttribute("newEditedPatient") @Validated Patient newEditedPatient, BindingResult bindingResult, Model model, @RequestParam String action, HttpSession session) {
        if (action.equals("save")) {
            if (bindingResult.hasErrors()) {
                model.addAttribute("editedPatient", editedPatient);
                model.addAttribute("banner", Banner.getBanner(Banner.CLIENT_BANNER, ClientPetsController.PAGE_ADDRESS));
                return PAGE_SOURCE;
            }
            newEditedPatient.setOwner(editedPatient.getOwner());
            patientRepository.save(newEditedPatient);
        }
        session.removeAttribute("editedPatient");
        return "redirect:" + ClientPetsController.PAGE_ADDRESS;
    }

    @GetMapping(PAGE_ADDRESS)
    public String getEditPet(@SessionAttribute("loggedUser") User loggedUser, Model model, HttpSession session) throws ServletRequestBindingException {
        if (!loggedUser.getUserType().equals(UserType.CLIENT_ID))
            throw new ServletRequestBindingException("");
        Patient editedPatient = (Patient) session.getAttribute("editedPatient");
        if (editedPatient == null)
            return "redirect:" + ClientPetsController.PAGE_ADDRESS;

        model.addAttribute("editedPatient", editedPatient);
        model.addAttribute("newEditedPatient", editedPatient);
        model.addAttribute("banner", Banner.getBanner(Banner.CLIENT_BANNER, ClientPetsController.PAGE_ADDRESS));
        return PAGE_SOURCE;
    }
}
