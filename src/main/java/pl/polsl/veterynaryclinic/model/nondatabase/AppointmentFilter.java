package pl.polsl.veterynaryclinic.model.nondatabase;

import org.springframework.format.annotation.DateTimeFormat;
import pl.polsl.veterynaryclinic.model.Patient;
import pl.polsl.veterynaryclinic.model.User;

import java.util.Date;
import java.util.List;

public class AppointmentFilter {

    private Float minimalTotalCost;

    private Float maximalTotalCost;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startingAppointmentDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endingAppointmentDate;

    private List<User> doctors;

    private List<Patient> patients;

    private List<Integer> clients;

    public AppointmentFilter() {
    }

    public Float getMinimalTotalCost() {
        return minimalTotalCost;
    }

    public void setMinimalTotalCost(Float minimalTotalCost) {
        this.minimalTotalCost = minimalTotalCost;
    }

    public Float getMaximalTotalCost() {
        return maximalTotalCost;
    }

    public void setMaximalTotalCost(Float maximalTotalCost) {
        this.maximalTotalCost = maximalTotalCost;
    }

    public Date getStartingAppointmentDate() {
        return startingAppointmentDate;
    }

    public void setStartingAppointmentDate(Date startingAppointmentDate) {
        this.startingAppointmentDate = startingAppointmentDate;
    }

    public Date getEndingAppointmentDate() {
        return endingAppointmentDate;
    }

    public void setEndingAppointmentDate(Date endingAppointmentDate) {
        this.endingAppointmentDate = endingAppointmentDate;
    }

    public List<User> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<User> doctors) {
        this.doctors = doctors;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public List<Integer> getClients() {
        return clients;
    }

    public void setClients(List<Integer> clients) {
        this.clients = clients;
    }
}
