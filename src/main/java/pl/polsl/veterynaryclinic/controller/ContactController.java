package pl.polsl.veterynaryclinic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.polsl.veterynaryclinic.constants.Banner;
import pl.polsl.veterynaryclinic.model.CompanyData;
import pl.polsl.veterynaryclinic.repository.CompanyDataRepository;

import javax.servlet.http.HttpSession;

@Controller
public class ContactController {

    private static final String PAGE_ADDRESS = "/contact";

    private static final String PAGE_SOURCE = "contact";

    @Autowired
    CompanyDataRepository companyDataRepository;

    @GetMapping(PAGE_ADDRESS)
    public String getContact(Model model, HttpSession session) {
        Banner.addSharedPageBannerToModel(model, session, PAGE_ADDRESS);
        CompanyData companyData = companyDataRepository.findByCompanyDataId(1);
        model.addAttribute("companyData", companyData);
        return PAGE_SOURCE;
    }

}
