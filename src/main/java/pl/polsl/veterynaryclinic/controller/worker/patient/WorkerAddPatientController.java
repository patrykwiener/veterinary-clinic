package pl.polsl.veterynaryclinic.controller.worker.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pl.polsl.veterynaryclinic.constants.Banner;
import pl.polsl.veterynaryclinic.constants.UserType;
import pl.polsl.veterynaryclinic.model.Patient;
import pl.polsl.veterynaryclinic.model.User;
import pl.polsl.veterynaryclinic.repository.PatientRepository;
import pl.polsl.veterynaryclinic.repository.UserRepository;
import pl.polsl.veterynaryclinic.validator.worker.WorkerPatientValidator;

import javax.servlet.http.HttpSession;

@Controller
public class WorkerAddPatientController {

    public static final String PAGE_ADDRESS = "/worker/patient/add_patient";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private WorkerPatientValidator workerPatientValidator;

    @InitBinder("newPatient")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(workerPatientValidator);
    }

    @GetMapping(PAGE_ADDRESS)
    public String getAddPatient(@SessionAttribute("loggedUser") User loggedUser, @ModelAttribute("newPatient") Patient newPatient, Model model) throws ServletRequestBindingException {
        if (!loggedUser.getUserType().equals(UserType.DOCTOR_ID)) {
            throw new ServletRequestBindingException("");
        }
        model.addAttribute("banner", Banner.getBanner(Banner.DOCTOR_BANNER, PAGE_ADDRESS));
        model.addAttribute("ownerList", userRepository.findAllByUserType(UserType.CLIENT_ID));
        return PAGE_ADDRESS;
    }

    @PostMapping(PAGE_ADDRESS)
    public String postAddPatient(@ModelAttribute("newPatient") Patient newPatient, Model model, BindingResult bindingResult, HttpSession session) {
        session.removeAttribute("patientToEdit");
        workerPatientValidator.validate(newPatient, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("banner", Banner.getBanner(Banner.DOCTOR_BANNER, PAGE_ADDRESS));
            model.addAttribute("ownerList", userRepository.findAllByUserType(UserType.CLIENT_ID));
            return PAGE_ADDRESS;
        }
        patientRepository.save(newPatient);
        return "redirect:" + PAGE_ADDRESS;
    }
}
