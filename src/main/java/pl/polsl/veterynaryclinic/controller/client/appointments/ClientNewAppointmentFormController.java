package pl.polsl.veterynaryclinic.controller.client.appointments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pl.polsl.veterynaryclinic.constants.Banner;
import pl.polsl.veterynaryclinic.constants.UserType;
import pl.polsl.veterynaryclinic.model.Appointment;
import pl.polsl.veterynaryclinic.model.Patient;
import pl.polsl.veterynaryclinic.model.User;
import pl.polsl.veterynaryclinic.repository.PatientRepository;
import pl.polsl.veterynaryclinic.repository.UserRepository;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes("appointment")
public class ClientNewAppointmentFormController {

    private static final String PAGE_ADDRESS = "/client/appointments/new_form";

    private static final String PAGE_SOURCE = "/client/appointments/new_form";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientRepository patientRepository;

    @InitBinder("newAppointment")
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @PostMapping(PAGE_ADDRESS)
    public String postNewAppointmentForm(@SessionAttribute("loggedUser") User loggedUser, @ModelAttribute("newAppointment") Appointment newAppointment, Model model, BindingResult bindingResult) {
        if (newAppointment.getAppointmentDate() == null)
            return "redirect:" + PAGE_ADDRESS;
        Date today = Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        if (newAppointment.getAppointmentDate().before(today)) {
            model.addAttribute("errorMessage", "Data nie może być z przeszłości");
            prepareModel(model, loggedUser);
            return PAGE_SOURCE;
        }
        model.addAttribute("appointment", newAppointment);
        return "redirect:/client/appointments/new";
    }

    @GetMapping(PAGE_ADDRESS)
    public String getNewAppointmentForm(@SessionAttribute("loggedUser") User loggedUser, @ModelAttribute("newAppointment") Appointment newAppointment, Model model) throws ServletRequestBindingException {
        if (!loggedUser.getUserType().equals(UserType.CLIENT_ID))
            throw new ServletRequestBindingException("");

        prepareModel(model, loggedUser);
        return PAGE_SOURCE;
    }

    private void prepareModel(Model model, User loggedUser) {
        List<Patient> userPets = patientRepository.findAllByOwnerAndDateOfDeathIsNull(loggedUser);
        Map<Patient, String> patientList = new LinkedHashMap<>();
        for (Patient pet : userPets) {
            patientList.put(pet, pet.getPatientName());
        }

        List<User> doctors = userRepository.findAllByUserType(UserType.DOCTOR_ID);
        Map<User, String> doctorList = new LinkedHashMap<>();
        for (User doctor : doctors) {
            doctorList.put(doctor, doctor.getFullName());
        }

        model.addAttribute("doctorList", doctorList);
        model.addAttribute("patientList", patientList);
        model.addAttribute("banner", Banner.getBanner(Banner.CLIENT_BANNER, ClientAppointmentsController.PAGE_ADDRESS));
    }
}
