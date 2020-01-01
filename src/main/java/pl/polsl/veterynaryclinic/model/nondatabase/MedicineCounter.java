package pl.polsl.veterynaryclinic.model.nondatabase;

public class MedicineCounter {
    private String medicineName;
    private long medicineCount;
    private Float soldItemsPrice;

    public MedicineCounter(String medicineName, long medicineCount, Float soldItemsPrice){
        this.medicineName = medicineName;
        this.medicineCount = medicineCount;
        this.soldItemsPrice = soldItemsPrice;
    }

    public long getMedicineCount() {
        return medicineCount;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public Float getSoldItemsPrice() {
        return soldItemsPrice;
    }
}
