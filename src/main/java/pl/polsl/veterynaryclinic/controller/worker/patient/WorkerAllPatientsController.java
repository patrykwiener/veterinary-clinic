package pl.polsl.veterynaryclinic.controller.worker.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import pl.polsl.veterynaryclinic.constants.Banner;
import pl.polsl.veterynaryclinic.constants.UserType;
import pl.polsl.veterynaryclinic.model.Patient;
import pl.polsl.veterynaryclinic.model.User;
import pl.polsl.veterynaryclinic.repository.PatientRepository;

import java.util.List;

@Controller
public class WorkerAllPatientsController {

    public static final String PAGE_ADDRESS = "/worker/patient/all_patients";

    @Autowired
    private PatientRepository patientRepository;

    @GetMapping(PAGE_ADDRESS)
    public String getAllPatients(@SessionAttribute("loggedUser") User loggedUser, Model model) throws ServletRequestBindingException {
        if (!loggedUser.getUserType().equals(UserType.DOCTOR_ID)) {
            throw new ServletRequestBindingException("");
        }
        model.addAttribute("banner", Banner.getBanner(Banner.DOCTOR_BANNER, PAGE_ADDRESS));
        List<Patient> livingPatientList = patientRepository.findAllByDateOfDeathIsNullOrderByOwner_LastName();
        model.addAttribute("livingPatientList", livingPatientList);
        List<Patient> deadPatientList = patientRepository.findAllByDateOfDeathIsNotNullOrderByOwner_LastName();
        model.addAttribute("deadPatientList", deadPatientList);
        return PAGE_ADDRESS;
    }

}
