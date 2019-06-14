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
import pl.polsl.veterynaryclinic.repository.AppointmentRepository;
import pl.polsl.veterynaryclinic.repository.PatientRepository;
import pl.polsl.veterynaryclinic.validator.worker.WorkerPatientValidator;

import javax.servlet.http.HttpSession;

@Controller
public class WorkerEditPatientController {
    public static final String PAGE_ADDRESS = "/worker/patient/edit_patient";

    private Patient patientToEdit;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private WorkerPatientValidator workerPatientValidator;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @InitBinder("editedPatient")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(workerPatientValidator);
    }

    @GetMapping(PAGE_ADDRESS)
    public String getEditPatient(@SessionAttribute("loggedUser") User loggedUser, @ModelAttribute("editedPatient") Patient editedPatient, Model model, HttpSession session) throws ServletRequestBindingException {
        if (!loggedUser.getUserType().equals(UserType.DOCTOR_ID)) {
            throw new ServletRequestBindingException("");
        }
        model.addAttribute("banner", Banner.getBanner(Banner.DOCTOR_BANNER, WorkerChoosePatientController.PAGE_ADDRESS));
        patientToEdit = (Patient)session.getAttribute("patient");
        if (patientToEdit == null)
            return "redirect:" +  WorkerChoosePatientController.PAGE_ADDRESS;
        patientToEdit = patientRepository.findByPatientId(patientToEdit.getPatientId());
        session.removeAttribute("patient");
        model.addAttribute("editedPatient", patientToEdit);
        return PAGE_ADDRESS;
    }

    @PostMapping(PAGE_ADDRESS)
    public String postEditPatient(@ModelAttribute("editedPatient") Patient editedPatient, BindingResult bindingResult, Model model, HttpSession session){
        session.setAttribute("patientToEdit", patientToEdit);
        Patient patient = patientRepository.findByPatientId(patientToEdit.getPatientId());
        patient.setPatientName(editedPatient.getPatientName());
        patient.setDateOfDeath(editedPatient.getDateOfDeath());
        workerPatientValidator.validate(patient, bindingResult);
        session.removeAttribute("patientToEdit");
        if (bindingResult.hasErrors()) {
            model.addAttribute("banner", Banner.getBanner(Banner.DOCTOR_BANNER, PAGE_ADDRESS));
            return PAGE_ADDRESS;
        }
        patientRepository.save(patient);
        appointmentRepository.deleteByPatientAndHasTakenPlace(patient, 0);

        return "redirect:" + WorkerChoosePatientController.PAGE_ADDRESS;
    }

}
