package pl.polsl.veterynaryclinic.controller.worker.appointments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pl.polsl.veterynaryclinic.constants.Banner;
import pl.polsl.veterynaryclinic.constants.UserType;
import pl.polsl.veterynaryclinic.model.*;
import pl.polsl.veterynaryclinic.model.nondatabase.MedicineCounter;
import pl.polsl.veterynaryclinic.model.nondatabase.PaymentFilter;
import pl.polsl.veterynaryclinic.repository.*;

import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class WorkerEditAppointmentController {
    public static final String PAGE_ADDRESS = "/worker/appointments/edit_appointment";

    private User client;
    private Appointment appointment;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private PaymentForServiceRepository paymentForServiceRepository;

    @Autowired
    private PaymentForMedicineRepository paymentForMedicineRepository;

    @InitBinder("paymentFilter")
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @GetMapping(PAGE_ADDRESS)
    public String getAddAppointment(@SessionAttribute("loggedUser") User loggedUser, @ModelAttribute("paymentFilter") PaymentFilter paymentFilter, Model model, HttpSession session) throws ServletRequestBindingException {
        if (!loggedUser.getUserType().equals(UserType.DOCTOR_ID)) {
            throw new ServletRequestBindingException("");
        }
        model.addAttribute("banner", Banner.getBanner(Banner.DOCTOR_BANNER, WorkerPresentAppointmentController.PAGE_ADDRESS));

        Integer appointmentId = (Integer) session.getAttribute("appointmentId");
        if (appointmentId == null) {
            return "redirect:" + WorkerFutureAppointmentsController.PAGE_ADDRESS;
        }
        session.removeAttribute("appointmentId");
        this.appointment = appointmentRepository.findByAppointmentId(appointmentId);
        this.client = appointment.getPatient().getOwner();

        model.addAttribute("owner", client.getFullName());
        model.addAttribute("patient", appointment.getPatient().getPatientName());

        Date appointmentDate = appointment.getAppointmentDate();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy hh:mm");
        String strDate = dateFormat.format(appointmentDate);
        model.addAttribute("appointmentDate", strDate);

        List<Service> serviceList = serviceRepository.findAll();
        model.addAttribute("serviceList", serviceList);


        List<MedicineCounter> medicineCounterList = medicineRepository.findMedicineCountList();

        model.addAttribute("medicineCounterList", medicineCounterList);

        return PAGE_ADDRESS;
    }


    @PostMapping(PAGE_ADDRESS)
    public String postAddAppointment(@ModelAttribute("paymentFilter") PaymentFilter paymentFilter, Model model, HttpSession session, BindingResult bindingResult) throws ServletRequestBindingException {

        List<Medicine> medicineToModifyList = new ArrayList<>();
        List<PaymentForMedicine> paymentForMedicineList = new ArrayList<>();
        List<PaymentForService> paymentForServiceList;

        paymentForServiceList = this.getPaymentForServices(paymentFilter.getServices());

        Iterator itMedicineNames = paymentFilter.getMedicineNames().iterator();
        Iterator itRequestedItems = paymentFilter.getRequestedItems().iterator();
        while (itMedicineNames.hasNext() && itRequestedItems.hasNext()) {
            Integer requestedItems = (Integer) itRequestedItems.next();
            if (requestedItems > 0) {
                List<Medicine> medicineList = medicineRepository.findAllByMedicineNameAndRemainingItemsGreaterThanOrderByPurchaseDate((String) itMedicineNames.next(), 0);
                boolean breakIndicator = false;
                for (Medicine medicine : medicineList) {
                    PaymentForMedicine paymentForMedicine = new PaymentForMedicine();
                    if (requestedItems > medicine.getRemainingItems()) {
                        requestedItems -= medicine.getRemainingItems();
                        paymentForMedicine.setQuantity(medicine.getRemainingItems());
                        medicine.setRemainingItems(0);
                    } else {
                        medicine.setRemainingItems(medicine.getRemainingItems() - requestedItems);
                        paymentForMedicine.setQuantity(requestedItems);
                        breakIndicator = true;
                    }
                    medicineToModifyList.add(medicine);
                    paymentForMedicine.setMedicine(medicine);
                    paymentForMedicine.setCost(medicine.getSoldItemsPrice());
                    paymentForMedicine.setClient(this.client);
                    paymentForMedicine.setAppointment(this.appointment);
                    paymentForMedicineList.add(paymentForMedicine);
                    if (breakIndicator) {
                        break;
                    }
                }
            }
        }
        this.appointment.setHasTakenPlace(1);
        paymentForServiceRepository.saveAll(paymentForServiceList);
        medicineRepository.saveAll(medicineToModifyList);
        paymentForMedicineRepository.saveAll(paymentForMedicineList);
        appointmentRepository.save(this.appointment);
        return "redirect:" + WorkerPresentAppointmentController.PAGE_ADDRESS;
    }


    private List<PaymentForService> getPaymentForServices(List<Service> services) {
        List<PaymentForService> paymentForServiceList = new ArrayList<>();
        for (Service service : services) {
            PaymentForService paymentForService = new PaymentForService();
            paymentForService.setClient(this.client);
            paymentForService.setAppointment(this.appointment);
            paymentForService.setCost(service.getServiceCost());
            paymentForService.setService(service);
            paymentForServiceList.add(paymentForService);
        }
        return paymentForServiceList;
    }

}
