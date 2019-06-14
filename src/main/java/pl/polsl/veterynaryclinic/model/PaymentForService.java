package pl.polsl.veterynaryclinic.model;

import javax.persistence.*;

@Entity
@Table(name = "oplaty_za_uslugi")
public class PaymentForService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_op_uslugi")
    private Integer paymentId;

    @Column(name = "cena_uslugi")
    private Float cost;

    @ManyToOne
    @JoinColumn(name = "id_klienta", nullable = false)
    private User client;

    @ManyToOne
    @JoinColumn(name = "id_wizyty", nullable = false)
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(name = "id_uslugi", nullable = false)
    private Service service;

    public PaymentForService() {

    }

    public PaymentForService(Float cost, User client, Appointment appointment, Service service) {
        super();
        this.cost = cost;
        this.client = client;
        this.appointment = appointment;
        this.service = service;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }


}
