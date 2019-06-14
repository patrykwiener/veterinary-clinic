package pl.polsl.veterynaryclinic.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pacjenci")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pacjenta")
    private Integer patientId;

    @Column(name = "imie_pacjenta")
    private String patientName;

    @Column(name = "data_zgonu")
    private Date dateOfDeath;

    @ManyToOne
    @JoinColumn(name = "id_wlasciciela", nullable = false)
    @JsonManagedReference
    private User owner;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "patient")
    @JsonBackReference
    private List<Appointment> appointments = new ArrayList<>();

    public Patient() {
        super();
    }

    public Patient(String patientName, Date dateOfDeath, User owner) {
        this.patientName = patientName;
        this.dateOfDeath = dateOfDeath;
        this.owner = owner;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }


    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }


    public Date getDateOfDeath() {
        return dateOfDeath;
    }


    public void setDateOfDeath(Date dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
    }


    public User getOwner() {
        return owner;
    }


    public void setOwner(User owner) {
        this.owner = owner;
    }

    @JsonBackReference
    public Patient getReference() {
        return this;
    }
}
