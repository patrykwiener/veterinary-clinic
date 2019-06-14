package pl.polsl.veterynaryclinic.model;

import javax.persistence.*;

@Entity
@Table(name = "dane_firmy")
public class CompanyData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dane")
    private Integer companyDataId;

    @Column(name = "nazwa_firmy")
    private String companyName;

    @Column(name="wlasciciel_firmy")
    private String companyOwner;

    @Column(name="adres_kraj")
    private String country;
    
    @Column(name="adres_woj")
    private String voivodeship;

    @Column(name="adres_powiat")
    private String county;

    @Column(name="adres_gmina")
    private String parish;

    @Column(name="adres_kod")
    private String postcode;

    @Column(name="adres_miejsc")
    private String city;

    @Column(name="adres_ulica_nr")
    private String street;

    @Column(name="adres_koresp")
    private String addressForLetters;

    @Column(name="nr_tel")
    private String phoneNumber;

    @Column(name="email")
    private String email;

    @Column(name="nip")
    private String taxNumber;

    public Integer getCompanyDataId() {
        return companyDataId;
    }

    public void setCompanyDataId(Integer companyDataId) {
        this.companyDataId = companyDataId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyOwner() {
        return companyOwner;
    }

    public void setCompanyOwner(String companyOwner) {
        this.companyOwner = companyOwner;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getVoivodeship() {
        return voivodeship;
    }

    public void setVoivodeship(String voivodeship) {
        this.voivodeship = voivodeship;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getParish() {
        return parish;
    }

    public void setParish(String parish) {
        this.parish = parish;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAddressForLetters() {
        return addressForLetters;
    }

    public void setAddressForLetters(String addressForLetters) {
        this.addressForLetters = addressForLetters;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }
}
