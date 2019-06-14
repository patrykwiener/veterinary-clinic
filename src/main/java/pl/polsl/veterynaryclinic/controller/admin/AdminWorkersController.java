package pl.polsl.veterynaryclinic.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.*;
import pl.polsl.veterynaryclinic.constants.Banner;
import pl.polsl.veterynaryclinic.constants.UserType;
import pl.polsl.veterynaryclinic.dao.UserDao;
import pl.polsl.veterynaryclinic.model.User;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes("editedWorker")
public class AdminWorkersController {
	
	@Autowired
	private UserDao userDao;

	static final String PAGE_ADDRESS = "/admin/admin_workers";

    private static final String PAGE_SOURCE = "/admin/admin_workers";

    @PostMapping(PAGE_ADDRESS)
    public String postAdminWorkers(@ModelAttribute("user") User user, @RequestParam String action, Model model) {
        switch (action) {
            case "add":
                return "redirect:/admin/admin_workers_add";
            //FOR FUTURE DEVELOPEMENT: removing workers
            /*case "remove":
            	if (user.getUserId() == null)
                    return "redirect:/admin/admin_workers";
            	userDao.deleteUser(user.getUserId());
                return "redirect:/admin/admin_workers";*/
            case "edit":
                if (user.getUserId() == null)
                    return "redirect:/admin/admin_workers";
                
                model.addAttribute("editedWorker", userDao.findUser(user.getUserId()));
                return "redirect:/admin/admin_workers_edit";
        }
        return "redirect:/admin/admin_workers";
    }

    @GetMapping(PAGE_ADDRESS)
    public String getAdminWorkers(@ModelAttribute("user") User user, @SessionAttribute("loggedUser") User loggedUser, Model model) throws ServletRequestBindingException {
        if (!loggedUser.getUserType().equals(UserType.ADMIN_ID))
            throw new ServletRequestBindingException("");


        List<User> users = userDao.findWorkers();
        
        
        Map<Integer, String> userList = new LinkedHashMap<>();
        for (User u : users) {
            userList.put(u.getUserId(), u.getFirstName() + " " +  u.getLastName());
        }

        model.addAttribute("editedWorker", user);
        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("userList", userList);
        model.addAttribute("banner", Banner.getBanner(Banner.ADMIN_BANNER, AdminWorkersController.PAGE_ADDRESS));
        return PAGE_SOURCE;
    }

}
