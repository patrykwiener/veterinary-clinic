package pl.polsl.veterynaryclinic.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Date; // it has to be java.util.Date, because java.sql.Date cannot store time of day

@Entity
@Table(name = "wizyty")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_wizyty")
    private Integer appointmentId;

    @Column(name = "kwota_calkowita")
    private Float totalCost;

    @Column(name = "data_wizyty")
    private Date appointmentDate;

    @Column(name = "czy_juz_sie_odbyla")
    private Integer hasTakenPlace;

    @ManyToOne
    @JoinColumn(name = "id_lekarza", nullable = false)
    @JsonManagedReference
    private User doctor;

    @ManyToOne
    @JoinColumn(name = "id_pacjenta", nullable = false)
    @JsonManagedReference
    private Patient patient;

    public Appointment() {
        // Default values for hasTakenPlace and totalCost
        this.hasTakenPlace = 0;
        this.totalCost = 0.0f;
    }

    public Appointment(Float totalCost, Date appointmentDate, Integer hasTakenPlace, User doctor, Patient patient) {
        super();
        this.totalCost = totalCost;
        this.appointmentDate = appointmentDate;
        this.hasTakenPlace = hasTakenPlace;
        this.doctor = doctor;
        this.patient = patient;
    }

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Float totalCost) {
        this.totalCost = totalCost;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Integer getHasTakenPlace() {
        return hasTakenPlace;
    }

    public void setHasTakenPlace(Integer hasTakenPlace) {
        this.hasTakenPlace = hasTakenPlace;
    }

    public User getDoctor() {
        return doctor;
    }

    public void setDoctor(User doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }


}
