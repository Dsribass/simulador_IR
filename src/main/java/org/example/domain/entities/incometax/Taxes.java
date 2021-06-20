package org.example.domain.entities.incometax;

public class Taxes {
    private Double taxesRate;
    private Double minimumLimit;
    private Double maximumLimit;

    public boolean isAboveTheLimit(Double amount){
        return amount > minimumLimit;
    }

    public Double getValueRangeTax(Double amountOfIncome){
        if(maximumLimit != null && amountOfIncome > maximumLimit){
            amountOfIncome = maximumLimit - minimumLimit;
            return (amountOfIncome) * taxesRate;
        }

        amountOfIncome -= minimumLimit;
        return (amountOfIncome) * taxesRate;
    }

    public Taxes(Double taxesRate, Double minimumLimit) {
        this(taxesRate,minimumLimit,null);
    }

    public Taxes(Double taxesRate, Double minimumLimit, Double maximumLimit) {
        this.taxesRate = taxesRate;
        this.minimumLimit = minimumLimit;
        this.maximumLimit = maximumLimit;
    }

    public Taxes() {
    }

    public Double getTaxesRate() {
        return taxesRate;
    }

    public void setTaxesRate(Double taxesRate) {
        this.taxesRate = taxesRate;
    }

    public Double getMinimumLimit() {
        return minimumLimit;
    }

    public void setMinimumLimit(Double minimumLimit) {
        this.minimumLimit = minimumLimit;
    }

    public Double getMaximumLimit() {
        return maximumLimit;
    }

    public void setMaximumLimit(Double maximumLimit) {
        this.maximumLimit = maximumLimit;
    }

    @Override
    public String toString() {
        return "Taxes{" +
                "taxesRate=" + taxesRate +
                ", minimumLimit=" + minimumLimit +
                ", maximumLimit=" + maximumLimit +
                '}';
    }
}
