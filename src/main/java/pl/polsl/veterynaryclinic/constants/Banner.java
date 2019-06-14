package pl.polsl.veterynaryclinic.constants;

import org.springframework.ui.Model;
import pl.polsl.veterynaryclinic.model.User;

import javax.servlet.http.HttpSession;

public abstract class Banner {

    public static final String[][] NOT_LOGGED_BANNER = {{"/", "Zaloguj"}, {"/contact", "Kontakt"}, {"/our_doctors", "Nasi lekarze"}};
    public static final String[][] CLIENT_BANNER = {{"/contact", "Kontakt"}, {"/our_doctors", "Nasi lekarze"}, {"/client/data/data", "Moje dane"}, {"/client/pets/pets", "Moje zwierzęta"}, {"/client/appointments/appointments", "Moje wizyty"}, {"/logout", "Wyloguj"}};
    public static final String[][] DOCTOR_BANNER = {{"/contact", "Kontakt"}, {"/our_doctors", "Nasi lekarze"}, {"Klient", "/worker/client/add_client", "Dodaj klienta", "/worker/client/choose_client", "Edytuj klienta"},{"Pacjent", "/worker/patient/add_patient", "Dodaj pacjenta", "/worker/patient/choose_patient", "Edytuj pacjenta", "/worker/patient/all_patients", "Wszyscy pacjenci"}, {"/worker/appointments/add_appointment", "Dodaj wizytę"}, {"Wizyty", "/worker/appointments/present", "Dzisiejsze wizyty", "/worker/appointments/history", "Historia wizyt", "/worker/appointments/future", "Przyszłe wizyty"}, {"/worker/report/report_parameters", "Raport"}, {"/worker/edit_personal_data", "Moje dane"}, {"/logout", "Wyloguj"}};
    public static final String[][] ADMIN_BANNER = {{"/contact", "Kontakt"}, {"/our_doctors", "Nasi lekarze"}, {"/admin/admin_workers", "Pracownicy"}, {"/admin/admin_clients", "Klienci"}, {"/admin/admin_services", "Cennik usług"}, {"/admin/admin_medicines", "Magazyn leków"}, {"/logout", "Wyloguj"}};


    public static String getBanner(String[][] userBanner, String pageAddress) {
        StringBuilder bannerBuilder = new StringBuilder();
        bannerBuilder.append("<div class=\"w3-bar w3-black\">");
        for (String[] bar : userBanner) {
            if (bar.length > 2) {   //serving dropdown hover menu
                boolean dropdownPageOpened = false;
                StringBuilder bannerDropdownBuilder = new StringBuilder();
                for (int i = 1; i < bar.length; i += 2) {
                    bannerDropdownBuilder
                            .append("<a href=\"")
                            .append(bar[i])
                            .append("\" class=\"w3-bar-item w3-button");
                    if (bar[i].equals(pageAddress)) {
                        bannerDropdownBuilder.append(" w3-blue");
                        dropdownPageOpened = true;
                    }
                    bannerDropdownBuilder
                            .append("\">")
                            .append(bar[i + 1])
                            .append("</a>");
                }
                bannerBuilder
                        .append("<div class=\"w3-dropdown-hover\"><button class=\"w3-button");
                if (dropdownPageOpened)
                    bannerBuilder.append(" w3-blue");
                bannerBuilder
                        .append("\">")
                        .append(bar[0])
                        .append("</button><div class=\"w3-dropdown-content w3-bar-block w3-card-4\">")
                        .append(bannerDropdownBuilder)
                        .append("</div></div>");
            } else {
                bannerBuilder.append("<a href=\"").append(bar[0]).append("\" class=\"w3-bar-item w3-button");
                if (bar[0].equals(pageAddress))
                    bannerBuilder.append(" w3-blue");
                bannerBuilder.append("\">").append(bar[1]).append("</a>");
            }
        }
        bannerBuilder.append("</div>");
        return bannerBuilder.toString();
    }

    public static void addSharedPageBannerToModel(Model model, HttpSession session, String pageAddress) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser != null)
            switch (loggedUser.getUserType()) {
                case UserType.CLIENT_ID:
                    model.addAttribute("banner", Banner.getBanner(Banner.CLIENT_BANNER, pageAddress));
                    break;
                case UserType.DOCTOR_ID:
                    model.addAttribute("banner", Banner.getBanner(Banner.DOCTOR_BANNER, pageAddress));
                    break;
                case UserType.ADMIN_ID:
                    model.addAttribute("banner", Banner.getBanner(Banner.ADMIN_BANNER, pageAddress));
                    break;
            }
        else
            model.addAttribute("banner", Banner.getBanner(Banner.NOT_LOGGED_BANNER, pageAddress));
    }
}
