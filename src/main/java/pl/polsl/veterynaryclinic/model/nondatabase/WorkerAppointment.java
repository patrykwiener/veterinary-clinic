package pl.polsl.veterynaryclinic.model.nondatabase;

import org.springframework.format.annotation.DateTimeFormat;
import pl.polsl.veterynaryclinic.model.User;

import java.util.Date;

public class WorkerAppointment {

    private Integer clientId;

    private Integer patientId;

    private User doctor;

    @DateTimeFormat(pattern = "yyyy-MM-dd','HH:mm")
    private Date appointmentDate;


    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public User getDoctor() {
        return doctor;
    }

    public void setDoctor(User doctor) {
        this.doctor = doctor;
    }
}
