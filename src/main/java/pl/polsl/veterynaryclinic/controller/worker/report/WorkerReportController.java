package pl.polsl.veterynaryclinic.controller.worker.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import pl.polsl.veterynaryclinic.constants.Banner;
import pl.polsl.veterynaryclinic.constants.UserType;
import pl.polsl.veterynaryclinic.model.PaymentForMedicine;
import pl.polsl.veterynaryclinic.model.PaymentForService;
import pl.polsl.veterynaryclinic.model.User;
import pl.polsl.veterynaryclinic.model.nondatabase.PaymentForMedicineSummary;
import pl.polsl.veterynaryclinic.repository.PaymentForMedicineRepository;
import pl.polsl.veterynaryclinic.repository.PaymentForServiceRepository;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
public class WorkerReportController {

    private Date startDate = null;
    private Date endDate = null;

    public static final String PAGE_ADDRESS = "/worker/report/report";

    @Autowired
    private PaymentForServiceRepository paymentForServiceRepository;

    @Autowired
    private PaymentForMedicineRepository paymentForMedicineRepository;

    @GetMapping(PAGE_ADDRESS)
    public String getReport(@SessionAttribute("loggedUser") User loggedUser, Model model, HttpSession session) throws ServletRequestBindingException {
        if (!loggedUser.getUserType().equals(UserType.DOCTOR_ID)) {
            throw new ServletRequestBindingException("");
        }
        model.addAttribute("banner", Banner.getBanner(Banner.DOCTOR_BANNER, PAGE_ADDRESS));
        startDate = (Date) session.getAttribute("startDate");
        endDate = (Date) session.getAttribute("endDate");
        if (startDate == null || endDate == null) {
            return "redirect:" + WorkerReportParametersController.PAGE_ADDRESS;
        }
        session.removeAttribute("startDate");
        session.removeAttribute("endDate");
        model.addAttribute("banner", Banner.getBanner(Banner.DOCTOR_BANNER, WorkerReportParametersController.PAGE_ADDRESS));

        endDate.setTime(endDate.getTime() + TimeUnit.DAYS.toMillis(1));

        List<PaymentForService> paymentForServiceList = paymentForServiceRepository.findAllByAppointment_AppointmentDateBetweenAndAppointment_DoctorOrderByAppointment_AppointmentDate(startDate, endDate, loggedUser);
        Float paymentForServicesBetweenDates;
        if (!paymentForServiceList.isEmpty()) {
            paymentForServicesBetweenDates = paymentForServiceRepository.findPaymentForServicesBetweenDates(startDate, endDate, loggedUser);
        } else{
            paymentForServicesBetweenDates = 0.0f;
        }
        model.addAttribute("paymentForServiceList", paymentForServiceList);
        model.addAttribute("paymentForServicesBetweenDates", paymentForServicesBetweenDates);

        List<PaymentForMedicine> paymentForMedicineList = paymentForMedicineRepository.findAllByAppointment_AppointmentDateBetweenAndAppointment_DoctorOrderByAppointment_AppointmentDate(startDate, endDate, loggedUser);
        PaymentForMedicineSummary paymentForMedicineSummary;
        if (!paymentForMedicineList.isEmpty()){
            paymentForMedicineSummary = paymentForMedicineRepository.findPaymentForMedicinesBetweenDates(startDate, endDate, loggedUser);
        } else{
            paymentForMedicineSummary = new PaymentForMedicineSummary();
        }
        model.addAttribute("paymentForMedicineList", paymentForMedicineList);
        model.addAttribute("purchasePriceSum", paymentForMedicineSummary.getPurchasePriceSum());
        model.addAttribute("soldPriceSum", paymentForMedicineSummary.getSoldPriceSum());
        model.addAttribute("profitSum", (float)Math.round((paymentForMedicineSummary.getSoldPriceSum() - paymentForMedicineSummary.getPurchasePriceSum())*100f)/100f);

        return PAGE_ADDRESS;
    }


}
