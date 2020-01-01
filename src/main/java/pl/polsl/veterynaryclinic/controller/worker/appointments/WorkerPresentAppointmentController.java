package pl.polsl.veterynaryclinic.controller.worker.appointments;

import org.apache.commons.lang3.time.DateUtils;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@SessionAttributes("appointmentId")
public class WorkerPresentAppointmentController {

    private Integer editAppointmentId;
    private Integer client_id;
    private List<Appointment> appointments;
    private List<Patient> patients;
    private List<User> clients;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserRepository userRepository;

    public static final String PAGE_ADDRESS = "/worker/appointments/present";

    @GetMapping(PAGE_ADDRESS)
    public String getCalendar(@SessionAttribute("loggedUser") User loggedUser, Model model) throws ServletRequestBindingException {
        if (!loggedUser.getUserType().equals(UserType.DOCTOR_ID)) {
            throw new ServletRequestBindingException("");
        }
        model.addAttribute("banner", Banner.getBanner(Banner.DOCTOR_BANNER, PAGE_ADDRESS));

        List<Date> dateList = getPresentAndTomorrowDate();
        appointments = appointmentRepository.findAllByDoctorAndAppointmentDateBetweenAndHasTakenPlaceOrderByAppointmentDate(loggedUser, dateList.get(0), dateList.get(1), 0);
        clients = userRepository.findDistinctByPets_AppointmentsIn(appointments);

        return PAGE_ADDRESS;
    }

    @PostMapping(PAGE_ADDRESS)
    public String postCalendar(Model model) {
        model.addAttribute("appointmentId", this.editAppointmentId);
        return "redirect:/worker/appointments/edit_appointment";
    }


    @RequestMapping(PAGE_ADDRESS + "/edit_appointment")
    public String edit_appointment(@RequestParam("id") String appointmentId) {
        this.editAppointmentId = Integer.parseInt(appointmentId);
        return "redirect:/worker/appointments/add_appointment";
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
    public void select_client(@RequestParam("id") String client_id, @SessionAttribute("loggedUser") User loggedUser) {
        List<Date> dateList = getPresentAndTomorrowDate();
        if (!client_id.equals("")) {
            this.client_id = Integer.parseInt(client_id);
            appointments = appointmentRepository.findAllByDoctorAndAppointmentDateBetweenAndPatient_Owner_UserIdAndHasTakenPlaceOrderByAppointmentDate(loggedUser, dateList.get(0), dateList.get(1), this.client_id, 0);
            patients = patientRepository.findDistinctByAppointmentsIn(appointments);
        } else {
            appointments = appointmentRepository.findAllByDoctorAndAppointmentDateBetweenAndHasTakenPlaceOrderByAppointmentDate(loggedUser, dateList.get(0), dateList.get(1), 0);
            patients = null;
        }
    }

    @RequestMapping(PAGE_ADDRESS + "/select_patient")
    @ResponseBody
    public void select_patient(@RequestParam("id") String patient_id, @SessionAttribute("loggedUser") User loggedUser) {
        List<Date> dateList = getPresentAndTomorrowDate();
        if (!patient_id.equals("")) {
            appointments = appointmentRepository.findAllByDoctorAndPatient_PatientIdAndPatient_Owner_UserIdAndAppointmentDateBetweenAndHasTakenPlaceOrderByAppointmentDate(loggedUser, Integer.parseInt(patient_id), this.client_id, dateList.get(0), dateList.get(1), 0);
        } else {
            appointments = appointmentRepository.findAllByDoctorAndAppointmentDateBetweenAndPatient_Owner_UserIdAndHasTakenPlaceOrderByAppointmentDate(loggedUser, dateList.get(0), dateList.get(1), this.client_id, 0);
        }
    }

    private List<Date> getPresentAndTomorrowDate() {
        Date today = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
        Date tomorrow = DateUtils.addDays(today, 1);
        List<Date> presentAndTomorrowDate = new ArrayList<>();
        presentAndTomorrowDate.add(today);
        presentAndTomorrowDate.add(tomorrow);
        return presentAndTomorrowDate;
    }


}
