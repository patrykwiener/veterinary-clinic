package pl.polsl.veterynaryclinic.controller.client.appointments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttribute;
import pl.polsl.veterynaryclinic.constants.Banner;
import pl.polsl.veterynaryclinic.constants.UserType;
import pl.polsl.veterynaryclinic.model.Appointment;
import pl.polsl.veterynaryclinic.model.User;
import pl.polsl.veterynaryclinic.model.nondatabase.AppointmentFilter;
import pl.polsl.veterynaryclinic.repository.AppointmentRepository;
import pl.polsl.veterynaryclinic.repository.AppointmentService;
import pl.polsl.veterynaryclinic.repository.PatientRepository;
import pl.polsl.veterynaryclinic.repository.UserRepository;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Controller
public class ClientAppointmentResultController {

    private static final String PAGE_ADDRESS_HISTORY = "/client/appointments/history";

    private static final String PAGE_SOURCE_HISTORY = "/client/appointments/history";

    private static final String PAGE_ADDRESS_FUTURE = "/client/appointments/future";

    private static final String PAGE_SOURCE_FUTURE = "/client/appointments/future";

    @Autowired
    private AppointmentService appointmentService;

    // --------------------------------------HISTORY-------------------------------------------------

    @GetMapping(PAGE_ADDRESS_HISTORY)
    public String getAppointmentHistory(@SessionAttribute("loggedUser") User loggedUser,
                                        Model model, HttpSession session) throws ServletRequestBindingException {
        if (!loggedUser.getUserType().equals(UserType.CLIENT_ID))
            throw new ServletRequestBindingException("");
        AppointmentFilter appointmentFilter = (AppointmentFilter) session.getAttribute("filledAppointmentFilter");
        if (appointmentFilter == null)
            return "redirect:" + ClientAppointmentFormController.PAGE_ADDRESS_HISTORY;

        List<Appointment> pastAppointments = appointmentService
                .findAllAppointmentsDynamically(appointmentFilter.getMinimalTotalCost(),
                                                appointmentFilter.getMaximalTotalCost(),
                                                appointmentFilter.getStartingAppointmentDate(),
                                                appointmentFilter.getEndingAppointmentDate(),
                                                appointmentFilter.getDoctors(), appointmentFilter.getPatients(), 1);
        model.addAttribute("pastAppointments", pastAppointments);
        model.addAttribute("banner", Banner.getBanner(Banner.CLIENT_BANNER, ClientAppointmentsController.PAGE_ADDRESS));
        return PAGE_SOURCE_HISTORY;
    }

    // ----------------------------------------------------------------------------------------------
    // --------------------------------------FUTURE--------------------------------------------------

    @GetMapping(PAGE_ADDRESS_FUTURE)
    public String getFutureAppointments(@SessionAttribute("loggedUser") User loggedUser,
                                        Model model, HttpSession session) throws ServletRequestBindingException {
        if (!loggedUser.getUserType().equals(UserType.CLIENT_ID))
            throw new ServletRequestBindingException("");
        AppointmentFilter appointmentFilter = (AppointmentFilter) session.getAttribute("filledAppointmentFilter");
        if (appointmentFilter == null)
            return "redirect:" + ClientAppointmentFormController.PAGE_ADDRESS_FUTURE;

        List<Appointment> futureAppointments = appointmentService
                .findAllAppointmentsDynamically(null, null, appointmentFilter.getStartingAppointmentDate(),
                                                appointmentFilter.getEndingAppointmentDate(),
                                                appointmentFilter.getDoctors(), appointmentFilter.getPatients(), 0);
        model.addAttribute("futureAppointments", futureAppointments);
        model.addAttribute("banner", Banner.getBanner(Banner.CLIENT_BANNER, ClientAppointmentsController.PAGE_ADDRESS));
        return PAGE_SOURCE_FUTURE;
    }

    // ----------------------------------------------------------------------------------------------
}
