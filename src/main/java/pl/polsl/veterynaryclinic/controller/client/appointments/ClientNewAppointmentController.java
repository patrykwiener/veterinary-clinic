package pl.polsl.veterynaryclinic.controller.client.appointments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pl.polsl.veterynaryclinic.constants.Banner;
import pl.polsl.veterynaryclinic.constants.UserType;
import pl.polsl.veterynaryclinic.model.Appointment;
import pl.polsl.veterynaryclinic.model.User;
import pl.polsl.veterynaryclinic.repository.AppointmentRepository;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class ClientNewAppointmentController {

    private static final String PAGE_ADDRESS = "/client/appointments/new";

    private static final String PAGE_SOURCE = "/client/appointments/new";

    @Autowired
    AppointmentRepository appointmentRepository;

    @InitBinder("newAppointment")
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @PostMapping(PAGE_ADDRESS)
    public String postNewAppointment(@ModelAttribute("newAppointment") Appointment newAppointment,
                                     @RequestParam String action, HttpSession session) {
        if (action.equals("save")) {
            if (newAppointment.getAppointmentDate() == null)
                return "redirect:" + PAGE_ADDRESS;
            Appointment appointment = (Appointment) session.getAttribute("appointment");
            newAppointment.setDoctor(appointment.getDoctor());
            newAppointment.setPatient(appointment.getPatient());
            appointmentRepository.save(newAppointment);
            session.removeAttribute("appointment");
        }
        return "redirect:" + ClientAppointmentsController.PAGE_ADDRESS;
    }

    @GetMapping(PAGE_ADDRESS)
    public String getNewAppointment(@SessionAttribute("loggedUser") User loggedUser,
                                    @ModelAttribute("newAppointment") Appointment newAppointment, Model model,
                                    HttpSession session) throws ServletRequestBindingException {
        if (!loggedUser.getUserType().equals(UserType.CLIENT_ID))
            throw new ServletRequestBindingException("");
        Appointment appointment = (Appointment) session.getAttribute("appointment");
        if (appointment == null)
            return "redirect:" + ClientAppointmentsController.PAGE_ADDRESS;

        LocalDateTime now = LocalDateTime.now();
        Date date = appointment.getAppointmentDate();
        LocalDate day = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDateTime startOfDay = day.atTime(8, 0);
        LocalDateTime endOfDay = day.atTime(16, 0);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        Map<Date, String> dateList = new LinkedHashMap<>();

        if (day.equals(now.toLocalDate())) {
            if (now.isBefore(endOfDay)) {
                while (startOfDay.isBefore(now))
                    startOfDay = startOfDay.plusMinutes(30);
            } else {
                startOfDay = endOfDay;
            }
        }
        for (LocalDateTime i = startOfDay; i.isBefore(endOfDay); i = i.plusMinutes(30)) {
            Date dateTime = Date.from(i.atZone(ZoneId.systemDefault()).toInstant());
            if (appointmentRepository.findByAppointmentDate(dateTime) == null)
                dateList.put(dateTime, i.format(timeFormatter));
        }

        model.addAttribute("availableDates", dateList);
        model.addAttribute("banner", Banner.getBanner(Banner.CLIENT_BANNER, ClientAppointmentsController.PAGE_ADDRESS));
        return PAGE_SOURCE;
    }
}
