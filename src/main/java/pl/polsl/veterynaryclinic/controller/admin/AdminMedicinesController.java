package pl.polsl.veterynaryclinic.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.*;
import pl.polsl.veterynaryclinic.constants.Banner;
import pl.polsl.veterynaryclinic.constants.UserType;
import pl.polsl.veterynaryclinic.dao.MedicineDao;
import pl.polsl.veterynaryclinic.model.Medicine;
import pl.polsl.veterynaryclinic.model.User;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes("editedMedicine")
public class AdminMedicinesController {

	@Autowired
	private MedicineDao medicineDao;

	static final String PAGE_ADDRESS = "/admin/admin_medicines";

    private static final String PAGE_SOURCE = "/admin/admin_medicines";

    @PostMapping(PAGE_ADDRESS)
    public String postAdminMedicines(@ModelAttribute("medicine") Medicine medicine, @RequestParam String action, Model model) {
        switch (action) {
            case "add":
                return "redirect:/admin/admin_medicines_add";
            //FOR FUTURE DEVELOPEMENT: removing medicines
            /*case "remove":
            	if (medicine.getMedicineId() == null)
                    return "redirect:/admin/admin_medicines";
            	medicineDao.deleteMedicine(medicine.getMedicineId());
                return "redirect:/admin/admin_medicines";*/
            case "edit":
                if (medicine.getMedicineId() == null)
                    return "redirect:/admin/admin_medicines";
                
                model.addAttribute("editedMedicine", medicineDao.findMedicine(medicine.getMedicineId()));
                return "redirect:/admin/admin_medicines_edit";
        }
        return "redirect:/admin/admin_medicines";
    }

    @GetMapping(PAGE_ADDRESS)
    public String getAdminMedicines(@ModelAttribute("medicine") Medicine medicine, @SessionAttribute("loggedUser") User loggedUser, Model model) throws ServletRequestBindingException {
        if (!loggedUser.getUserType().equals(UserType.ADMIN_ID))
            throw new ServletRequestBindingException("");


        List<Medicine> medicines = medicineDao.findAll();
        
        
        Map<Integer, String> medicineList = new LinkedHashMap<>();
        for (Medicine m : medicines) {
            medicineList.put(m.getMedicineId(), m.getMedicineName());
        }

        model.addAttribute("editedMedicine", medicine);
        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("medicineList", medicineList);
        model.addAttribute("banner", Banner.getBanner(Banner.ADMIN_BANNER, AdminMedicinesController.PAGE_ADDRESS));
        return PAGE_SOURCE;
    }
	
}
