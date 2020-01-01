package pl.polsl.veterynaryclinic.controller.client.pets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.*;
import pl.polsl.veterynaryclinic.constants.Banner;
import pl.polsl.veterynaryclinic.constants.UserType;
import pl.polsl.veterynaryclinic.model.Patient;
import pl.polsl.veterynaryclinic.model.User;
import pl.polsl.veterynaryclinic.repository.PatientRepository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes("editedPatient")
public class ClientPetsController {

    public static final String PAGE_ADDRESS = "/client/pets/pets";

    private static final String PAGE_SOURCE = "/client/pets/pets";

    @Autowired
    PatientRepository patientRepository;

    @PostMapping(PAGE_ADDRESS)
    public String postClientPets(@ModelAttribute("patient") Patient patient, @RequestParam String action, Model model) {
        switch (action) {
            case "add":
                return "redirect:" + ClientAddPetController.PAGE_ADDRESS;
            case "remove":
                if (patient.getPatientId() == null)
                    return "redirect:" + ClientPetsController.PAGE_ADDRESS;
                model.addAttribute("editedPatient", patientRepository.findById(patient.getPatientId()).get());
                return "redirect:" + ClientDeletePetController.PAGE_ADDRESS;
            case "edit":
                if (patient.getPatientId() == null)
                    return "redirect:" + ClientPetsController.PAGE_ADDRESS;
                model.addAttribute("editedPatient", patientRepository.findById(patient.getPatientId()).get());
                return "redirect:" + ClientEditPetController.PAGE_ADDRESS;
        }
        return "redirect:" + ClientPetsController.PAGE_ADDRESS;
    }

    @GetMapping(PAGE_ADDRESS)
    public String getClientPets(@ModelAttribute("patient") Patient patient, @SessionAttribute("loggedUser") User loggedUser, Model model) throws ServletRequestBindingException {
        if (!loggedUser.getUserType().equals(UserType.CLIENT_ID))
            throw new ServletRequestBindingException("");

        List<Patient> userPets = patientRepository.findAllByOwnerAndDateOfDeathIsNull(loggedUser);

        Map<Integer, String> patientList = new LinkedHashMap<>();
        for (Patient p : userPets) {
            patientList.put(p.getPatientId(), p.getPatientName());
        }

        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("patientList", patientList);
        model.addAttribute("banner", Banner.getBanner(Banner.CLIENT_BANNER, ClientPetsController.PAGE_ADDRESS));
        return PAGE_SOURCE;
    }
}
