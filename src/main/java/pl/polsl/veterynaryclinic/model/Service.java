package pl.polsl.veterynaryclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cennik")
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_uslugi")
    private Integer serviceId;

    @Column(name = "cena_uslugi")
    private Float serviceCost;

    @Column(name = "rodzaj_uslugi")
    private String serviceType;

    public Service() {

    }

    public Service(Float serviceCost, String serviceType) {
        super();
        this.serviceCost = serviceCost;
        this.serviceType = serviceType;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public Float getServiceCost() {
        return serviceCost;
    }

    public void setServiceCost(Float serviceCost) {
        this.serviceCost = serviceCost;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }


}
