package pl.polsl.veterynaryclinic.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "leki")
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_leku")
    private Integer medicineId;

    @Column(name = "nazwa_leku")
    private String medicineName;

    @Column(name = "liczba_zakupionych_sztuk")
    private Integer purchasedItems;

    @Column(name = "liczba_pozostalych_sztuk")
    private Integer remainingItems;

    @Column(name = "jednostka")
    private String unit;

    @Column(name = "cena_kupna")
    private Float purchasePrice;

    @Column(name = "data_kupna")
    private Date purchaseDate;

    @Column(name = "cena_sp_szt")
    private Float soldItemsPrice;

    public Medicine() {
        purchasedItems = 0;
        remainingItems = 0;
        purchasePrice = 0.0f;
        soldItemsPrice = 0.0f;
    }

    public Medicine(String medicineName, Integer purchasedItems, Integer remainingItems, String unit, Float purchasePrice,
                    Date purchaseDate, Float soldItemsPrice) {
        super();
        this.medicineName = medicineName;
        this.purchasedItems = purchasedItems;
        this.remainingItems = remainingItems;
        this.unit = unit;
        this.purchasePrice = purchasePrice;
        this.purchaseDate = purchaseDate;
        this.soldItemsPrice = soldItemsPrice;
    }

    public Integer getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(Integer medicineId) {
        this.medicineId = medicineId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public Integer getPurchasedItems() {
        return purchasedItems;
    }

    public void setPurchasedItems(Integer purchasedItems) {
        this.purchasedItems = purchasedItems;
    }

    public Integer getRemainingItems() {
        return remainingItems;
    }

    public void setRemainingItems(Integer remainingItems) {
        this.remainingItems = remainingItems;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Float getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Float purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Float getSoldItemsPrice() {
        return soldItemsPrice;
    }

    public void setSoldItemsPrice(Float soldItemsPrice) {
        this.soldItemsPrice = soldItemsPrice;
    }


}
