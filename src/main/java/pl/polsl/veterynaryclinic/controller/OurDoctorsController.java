package pl.polsl.veterynaryclinic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.polsl.veterynaryclinic.constants.Banner;
import pl.polsl.veterynaryclinic.constants.UserType;
import pl.polsl.veterynaryclinic.repository.UserRepository;

import javax.servlet.http.HttpSession;

@Controller
public class OurDoctorsController {

    private static final String PAGE_ADDRESS = "/our_doctors";

    private static final String PAGE_SOURCE = "our_doctors";

    @Autowired
    private UserRepository userRepository;

    @GetMapping(PAGE_ADDRESS)
    public String getOurDoctors(Model model, HttpSession session){
        Banner.addSharedPageBannerToModel(model, session, PAGE_ADDRESS);
        model.addAttribute("allDoctors", userRepository.findAllByUserType(UserType.DOCTOR_ID));
        return PAGE_SOURCE;
    }


}
