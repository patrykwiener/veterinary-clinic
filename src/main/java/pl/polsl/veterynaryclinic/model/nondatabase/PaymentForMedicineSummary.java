package pl.polsl.veterynaryclinic.model.nondatabase;

public class PaymentForMedicineSummary {

    private Float purchasePriceSum;
    private Float soldPriceSum;

    public PaymentForMedicineSummary(double purchasePriceSum, double soldPriceSum) {
        this.purchasePriceSum = (float) purchasePriceSum;
        this.soldPriceSum = (float) soldPriceSum;
    }

    public PaymentForMedicineSummary() {
        this.purchasePriceSum = 0.0f;
        this.soldPriceSum = 0.0f;
    }

    public Float getPurchasePriceSum() {
        return purchasePriceSum;
    }

    public void setPurchasePriceSum(Float purchasePriceSum) {
        this.purchasePriceSum = purchasePriceSum;
    }

    public Float getSoldPriceSum() {
        return soldPriceSum;
    }

    public void setSoldPriceSum(Float soldPriceSum) {
        this.soldPriceSum = soldPriceSum;
    }
}
