package pl.polsl.veterynaryclinic.controller.worker.appointments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pl.polsl.veterynaryclinic.constants.Banner;
import pl.polsl.veterynaryclinic.constants.UserType;
import pl.polsl.veterynaryclinic.model.Appointment;
import pl.polsl.veterynaryclinic.model.Patient;
import pl.polsl.veterynaryclinic.model.User;
import pl.polsl.veterynaryclinic.model.nondatabase.WorkerAppointment;
import pl.polsl.veterynaryclinic.repository.AppointmentRepository;
import pl.polsl.veterynaryclinic.repository.PatientRepository;
import pl.polsl.veterynaryclinic.repository.UserRepository;
import pl.polsl.veterynaryclinic.validator.worker.WorkerAppointmentValidator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class WorkerAddAppointmentController {

    public static final String PAGE_ADDRESS = "/worker/appointments/add_appointment";

    private Integer client_id;
    private Integer patient_id;
    private List<Patient> patients;
    private List<User> clients;
    private User loggedUser;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WorkerAppointmentValidator workerAppointmentValidator;

    @InitBinder("appointmentAddition")
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd','HH:mm");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.addValidators(workerAppointmentValidator);
    }

    @GetMapping(PAGE_ADDRESS)
    public String getAddAppointment(@SessionAttribute("loggedUser") User loggedUser, Model model, @ModelAttribute("appointmentAddition") WorkerAppointment workerAppointment) throws ServletRequestBindingException {
        if (!loggedUser.getUserType().equals(UserType.DOCTOR_ID)) {
            throw new ServletRequestBindingException("");
        }
        this.loggedUser = loggedUser;
        model.addAttribute("banner", Banner.getBanner(Banner.DOCTOR_BANNER, PAGE_ADDRESS));

        this.clients = userRepository.findAllByUserType(UserType.CLIENT_ID);
        this.patients = null;
        this.client_id = null;
        this.patient_id = null;

        return PAGE_ADDRESS;
    }

    @PostMapping(PAGE_ADDRESS)
    public String postNewAppointmentForm(@ModelAttribute("appointmentAddition") WorkerAppointment workerAppointment, Model model, BindingResult bindingResult) {

        model.addAttribute("chosenClient", this.client_id);
        model.addAttribute("chosenPatient", this.patient_id);

        workerAppointment.setDoctor(loggedUser);
        workerAppointmentValidator.validate(workerAppointment, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("banner", Banner.getBanner(Banner.DOCTOR_BANNER, PAGE_ADDRESS));

            return PAGE_ADDRESS;
        }

        Appointment newAppointment = new Appointment();
        newAppointment.setDoctor(loggedUser);
        newAppointment.setPatient(patientRepository.findByPatientId(workerAppointment.getPatientId()));
        newAppointment.setAppointmentDate(workerAppointment.getAppointmentDate());

        appointmentRepository.save(newAppointment);

        return "redirect:" + PAGE_ADDRESS;
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
            this.client_id = Integer.parseInt(client_id);
            patients = patientRepository.findAllByOwner_userIdAndDateOfDeathNull(this.client_id);
        } else {
            patients = null;
            this.client_id = null;
        }
        this.patient_id = null;
    }


    @RequestMapping(PAGE_ADDRESS + "/select_patient")
    @ResponseBody
    public void select_patient(@RequestParam("id") String patient_id) {

        if (!patient_id.equals("")) {
            this.patient_id = Integer.parseInt(patient_id);
        } else {
            this.patient_id = null;
        }
    }

}
