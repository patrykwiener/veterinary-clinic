package pl.polsl.veterynaryclinic.model;

import javax.persistence.*;

@Entity
@Table(name = "oplaty_za_leki")
public class PaymentForMedicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_op_leku")
    private Integer paymentId;

    @Column(name = "cena")
    private Float cost;

    @Column(name = "ilosc")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "id_klienta", nullable = false)
    private User client;

    @ManyToOne
    @JoinColumn(name = "id_wizyty", nullable = false)
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(name = "id_leku", nullable = false)
    private Medicine medicine;

    public PaymentForMedicine() {
        this.cost = 0.0f;
    }

    public PaymentForMedicine(Float cost, Integer quantity, User client, Appointment appointment, Medicine medicine) {
        super();
        this.cost = cost;
        this.quantity = quantity;
        this.client = client;
        this.appointment = appointment;
        this.medicine = medicine;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }


}
