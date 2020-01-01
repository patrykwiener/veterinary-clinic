package pl.polsl.veterynaryclinic.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "uzytkownicy")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_uzytkownika")
    private Integer userId;

    @Column(name = "imie")
    private String firstName;

    @Column(name = "nazwisko")
    private String lastName;

    @Column(name = "rodzaj_uzytkownika")
    private Character userType;

    @Column(name = "login")
    private String login;

    @Column(name = "haslo")
    private String password;

    @Column(name = "numer_telefonu")
    private String phoneNumber;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "owner")
    @JsonBackReference
    private List<Patient> pets = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "doctor")
    @JsonBackReference
    private List<Appointment> appointments = new ArrayList<>();

    @Transient
    private String confirmPassword;

    public User() {

    }

    public User(String firstName, String lastName, Character userType, String login, String password, String phoneNumber) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.userType = userType;
        this.login = login;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer id) {
        this.userId = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Character getUserType() {
        return userType;
    }

    public void setUserType(Character userType) {
        this.userType = userType;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public List<Patient> getPets() {
        return pets;
    }

    @JsonBackReference
    public User getReference() {
        return this;
    }
}
