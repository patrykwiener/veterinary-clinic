package pl.polsl.veterynaryclinic.controller.worker.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pl.polsl.veterynaryclinic.constants.Banner;
import pl.polsl.veterynaryclinic.constants.UserType;
import pl.polsl.veterynaryclinic.model.Patient;
import pl.polsl.veterynaryclinic.model.User;
import pl.polsl.veterynaryclinic.repository.PatientRepository;
import pl.polsl.veterynaryclinic.repository.UserRepository;

import java.util.List;

@Controller
@SessionAttributes("patient")
public class WorkerChoosePatientController {

    private List<Patient> patients;
    private List<User> clients;

    public static final String PAGE_ADDRESS = "/worker/patient/choose_patient";

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserRepository userRepository;

    @InitBinder("chosenPatient")
    public void initBinder(WebDataBinder binder) {

    }

    @GetMapping(PAGE_ADDRESS)
    public String getChoosePatient(@SessionAttribute("loggedUser") User loggedUser, @ModelAttribute("chosenPatient") Patient chosenPatient, Model model) throws ServletRequestBindingException {
        if (!loggedUser.getUserType().equals(UserType.DOCTOR_ID)) {
            throw new ServletRequestBindingException("");
        }
        model.addAttribute("banner", Banner.getBanner(Banner.DOCTOR_BANNER, PAGE_ADDRESS));

        this.clients = userRepository.findAllByUserType(UserType.CLIENT_ID);
        this.patients = null;

        return PAGE_ADDRESS;
    }

    @PostMapping(PAGE_ADDRESS)
    public String postChoosePatient(@ModelAttribute("chosenPatient") Patient chosenPatient, Model model) {
        model.addAttribute("patient", chosenPatient);
        return "redirect:" + WorkerEditPatientController.PAGE_ADDRESS;
    }


    @RequestMapping(PAGE_ADDRESS + "/get_clients")
    @ResponseBody
    public List<User> get_clients() {
        return this.clients;
    }


    @RequestMapping(PAGE_ADDRESS + "/get_patients")
    @ResponseBody
    public List<Patient> get_patients() {
        return this.patients;
    }


    @RequestMapping(PAGE_ADDRESS + "/select_client")
    @ResponseBody
    public void select_client(@RequestParam("id") String client_id) {

        if (!client_id.equals("")) {
            patients = patientRepository.findAllByOwner_userIdAndDateOfDeathNull(Integer.parseInt(client_id));
        } else {
            patients = null;

        }
    }

}
