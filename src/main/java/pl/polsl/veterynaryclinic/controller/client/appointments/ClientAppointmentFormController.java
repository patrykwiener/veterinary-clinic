package pl.polsl.veterynaryclinic.controller.client.appointments;

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
import pl.polsl.veterynaryclinic.model.nondatabase.AppointmentFilter;
import pl.polsl.veterynaryclinic.repository.PatientRepository;
import pl.polsl.veterynaryclinic.repository.UserRepository;
import pl.polsl.veterynaryclinic.validator.client.AppointmentFilterValidator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@SessionAttributes("filledAppointmentFilter")
public class ClientAppointmentFormController {

    static final String PAGE_ADDRESS_HISTORY = "/client/appointments/history_form";
    static final String PAGE_ADDRESS_FUTURE = "/client/appointments/future_form";
    private static final String PAGE_SOURCE_HISTORY = "/client/appointments/history_form";
    private static final String PAGE_SOURCE_FUTURE = "/client/appointments/future_form";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentFilterValidator appointmentFilterValidator;

    @InitBinder("appointmentFilter")
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.addValidators(appointmentFilterValidator);
    }

    // --------------------------------------HISTORY-------------------------------------------------

    @PostMapping(PAGE_ADDRESS_HISTORY)
    public String postAppointmentHistoryForm(@SessionAttribute("loggedUser") User loggedUser,
                                             @ModelAttribute("appointmentFilter") @Validated AppointmentFilter appointmentFilter,
                                             BindingResult bindingResult,
                                             Model model) {
        if (bindingResult.hasErrors()) {
            List<Patient> patientList = patientRepository.findAllByOwner(loggedUser);
            model.addAttribute("patientList", patientList);
            prepareForm(model);
            return PAGE_SOURCE_HISTORY;
        }
        model.addAttribute("filledAppointmentFilter", appointmentFilter);
        return "redirect:/client/appointments/history";
    }

    @GetMapping(PAGE_ADDRESS_HISTORY)
    public String getAppointmentsHistoryForm(@SessionAttribute("loggedUser") User loggedUser,
                                             @ModelAttribute("appointmentFilter") AppointmentFilter appointmentFilter,
                                             Model model) throws ServletRequestBindingException {
        if (!loggedUser.getUserType().equals(UserType.CLIENT_ID))
            throw new ServletRequestBindingException("");

        List<Patient> patientList = patientRepository.findAllByOwner(loggedUser);
        model.addAttribute("patientList", patientList);
        prepareForm(model);
        return PAGE_SOURCE_HISTORY;
    }

    // ----------------------------------------------------------------------------------------------
    // --------------------------------------FUTURE--------------------------------------------------

    @PostMapping(PAGE_ADDRESS_FUTURE)
    public String postAppointmentFutureForm(@SessionAttribute("loggedUser") User loggedUser,
                                            @ModelAttribute("appointmentFilter") @Validated AppointmentFilter appointmentFilter,
                                            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<Patient> patientList = patientRepository.findAllByOwnerAndDateOfDeathIsNull(loggedUser);
            model.addAttribute("patientList", patientList);
            prepareForm(model);
            return PAGE_SOURCE_FUTURE;
        }
        model.addAttribute("filledAppointmentFilter", appointmentFilter);
        return "redirect:/client/appointments/future";
    }

    @GetMapping(PAGE_ADDRESS_FUTURE)
    public String getAppointmentsFutureForm(@SessionAttribute("loggedUser") User loggedUser,
                                            @ModelAttribute("appointmentFilter") AppointmentFilter appointmentFilter,
                                            Model model) throws ServletRequestBindingException {
        if (!loggedUser.getUserType().equals(UserType.CLIENT_ID))
            throw new ServletRequestBindingException("");

        List<Patient> patientList = patientRepository.findAllByOwnerAndDateOfDeathIsNull(loggedUser);
        model.addAttribute("patientList", patientList);
        prepareForm(model);
        return PAGE_SOURCE_FUTURE;
    }

    // ----------------------------------------------------------------------------------------------

    private void prepareForm(Model model) {
        List<User> doctorList = userRepository.findAllByUserType(UserType.DOCTOR_ID);

        model.addAttribute("doctorList", doctorList);
        model.addAttribute("banner", Banner.getBanner(Banner.CLIENT_BANNER, ClientAppointmentsController.PAGE_ADDRESS));
    }
}
