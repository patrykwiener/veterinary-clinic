package pl.polsl.veterynaryclinic.controller.worker.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pl.polsl.veterynaryclinic.constants.Banner;
import pl.polsl.veterynaryclinic.constants.UserType;
import pl.polsl.veterynaryclinic.model.User;
import pl.polsl.veterynaryclinic.model.nondatabase.WorkerReport;
import pl.polsl.veterynaryclinic.validator.worker.WorkerReportValidator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@SessionAttributes({"startDate", "endDate"})
public class WorkerReportParametersController {

    public static final String PAGE_ADDRESS = "/worker/report/report_parameters";

    @Autowired
    private WorkerReportValidator workerReportValidator;

    @InitBinder("workerReport")
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.addValidators(workerReportValidator);
    }

    @GetMapping(PAGE_ADDRESS)
    public String getReportParameters(@SessionAttribute("loggedUser") User loggedUser, @ModelAttribute("workerReport") WorkerReport workerReport, Model model) throws ServletRequestBindingException {
        if (!loggedUser.getUserType().equals(UserType.DOCTOR_ID)) {
            throw new ServletRequestBindingException("");
        }
        model.addAttribute("banner", Banner.getBanner(Banner.DOCTOR_BANNER, PAGE_ADDRESS));

        return PAGE_ADDRESS;
    }

    @PostMapping(PAGE_ADDRESS)
    public String postReportParameters(@ModelAttribute("workerReport") WorkerReport workerReport, BindingResult bindingResult, Model model){
        workerReportValidator.validate(workerReport, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("banner", Banner.getBanner(Banner.DOCTOR_BANNER, PAGE_ADDRESS));
            return PAGE_ADDRESS;
        }

        model.addAttribute("startDate", workerReport.getStartDate());
        model.addAttribute("endDate", workerReport.getEndDate());

        return "redirect:" + WorkerReportController.PAGE_ADDRESS;
    }


}
