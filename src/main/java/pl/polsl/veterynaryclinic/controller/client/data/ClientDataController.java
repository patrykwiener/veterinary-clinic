package pl.polsl.veterynaryclinic.controller.client.data;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import pl.polsl.veterynaryclinic.constants.Banner;
import pl.polsl.veterynaryclinic.constants.UserType;
import pl.polsl.veterynaryclinic.model.User;

@Controller
public class ClientDataController {

    public static final String PAGE_ADDRESS = "/client/data/data";

    private static final String PAGE_SOURCE = "/client/data/data";

    @GetMapping(PAGE_ADDRESS)
    public String getMyData(@SessionAttribute("loggedUser")User loggedUser, Model model) throws ServletRequestBindingException {
        if (!loggedUser.getUserType().equals(UserType.CLIENT_ID))
            throw new ServletRequestBindingException("");

        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("banner", Banner.getBanner(Banner.CLIENT_BANNER, PAGE_ADDRESS));
        return PAGE_SOURCE;
    }
}
