package pl.polsl.veterynaryclinic.model.nondatabase;

import pl.polsl.veterynaryclinic.model.Service;

import java.util.ArrayList;
import java.util.List;

public class PaymentFilter {

    private List<Service> services;
    private List<String> medicineNames;
    private List<Integer> requestedItems;


    public PaymentFilter(){
        services = new ArrayList<>();
        medicineNames = new ArrayList<>();
        requestedItems = new ArrayList<>();
    }


    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public List<Integer> getRequestedItems() {
        return requestedItems;
    }

    public void setRequestedItems(List<Integer> requestedItems) {
        this.requestedItems = requestedItems;
    }

    public List<String> getMedicineNames() {
        return medicineNames;
    }

    public void setMedicineNames(List<String> medicineNames) {
        this.medicineNames = medicineNames;
    }
}
