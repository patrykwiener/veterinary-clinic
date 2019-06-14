package pl.polsl.veterynaryclinic.controller.worker.appointments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.*;
import pl.polsl.veterynaryclinic.constants.Banner;
import pl.polsl.veterynaryclinic.constants.UserType;
import pl.polsl.veterynaryclinic.model.Appointment;
import pl.polsl.veterynaryclinic.model.Patient;
import pl.polsl.veterynaryclinic.model.User;
import pl.polsl.veterynaryclinic.repository.AppointmentRepository;
import pl.polsl.veterynaryclinic.repository.PatientRepository;
import pl.polsl.veterynaryclinic.repository.UserRepository;

import java.util.Date;
import java.util.List;

@Controller
public class WorkerFutureAppointmentsController {

    private Integer client_id;
    private List<Appointment> appointments;
    private List<Patient> patients;
    private List<User> clients;
    private User loggedUser;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserRepository userRepository;

    public static final String PAGE_ADDRESS = "/worker/appointments/future";


    @GetMapping(PAGE_ADDRESS)
    public String getCalendar(@SessionAttribute("loggedUser") User loggedUser, Model model) throws ServletRequestBindingException {
        if (!loggedUser.getUserType().equals(UserType.DOCTOR_ID)) {
            throw new ServletRequestBindingException("");
        }
        model.addAttribute("banner", Banner.getBanner(Banner.DOCTOR_BANNER, PAGE_ADDRESS));
        this.loggedUser = loggedUser;
        appointments = appointmentRepository.findAllByDoctorAndAppointmentDateGreaterThanOrderByAppointmentDate(this.loggedUser, new Date());
        clients = userRepository.findDistinctByPets_AppointmentsIn(appointments);

        return PAGE_ADDRESS;
    }

    @RequestMapping(PAGE_ADDRESS + "/get_appointments")
    @ResponseBody
    public List<Appointment> get_appointments() {
        return this.appointments;
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
            appointments = appointmentRepository.findAllByDoctorAndAppointmentDateGreaterThanAndPatient_Owner_UserIdOrderByAppointmentDate(loggedUser, new Date(), this.client_id);
            patients = patientRepository.findDistinctByAppointmentsIn(appointments);
        } else {
            appointments = appointmentRepository.findAllByDoctorAndAppointmentDateGreaterThanOrderByAppointmentDate(loggedUser, new Date());
            patients = null;
        }
    }

    @RequestMapping(PAGE_ADDRESS + "/select_patient")
    @ResponseBody
    public void select_patient(@RequestParam("id") String patient_id) {

        if (!patient_id.equals("")) {
            appointments = appointmentRepository.findAllByDoctorAndPatient_PatientIdAndPatient_Owner_UserIdAndAppointmentDateGreaterThanOrderByAppointmentDate(loggedUser, Integer.parseInt(patient_id), this.client_id, new Date());
        } else {
            appointments = appointmentRepository.findAllByDoctorAndAppointmentDateGreaterThanAndPatient_Owner_UserIdOrderByAppointmentDate(loggedUser, new Date(), this.client_id);
        }
    }

}
