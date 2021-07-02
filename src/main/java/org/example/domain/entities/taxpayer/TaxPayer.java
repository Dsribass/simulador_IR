package org.example.domain.entities.taxpayer;

import org.example.domain.entities.expenses.Expense;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class TaxPayer {
    private String id;
    private String name;
    private Double annualTaxableIncome;
    private Double taxWithholding;
    private Double totalExpenses;

    public TaxPayer(String id, String name, Double annualTaxableIncome, Double taxWithholding) {
        this.id = id;
        this.name = name;
        this.annualTaxableIncome = annualTaxableIncome;
        this.taxWithholding = taxWithholding;
    }

    public TaxPayer(String name, Double annualTaxableIncome, Double taxWithholding) {
        this(UUID.randomUUID().toString(),name,annualTaxableIncome,taxWithholding);
    }

    public TaxPayer() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setId() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
    }

    public Double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(Double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public Double getAnnualTaxableIncome() {
        return annualTaxableIncome;
    }

    public void setAnnualTaxableIncome(Double annualTaxableIncome) {
        this.annualTaxableIncome = annualTaxableIncome;
    }

    public Double getTaxWithholding() {
        return taxWithholding;
    }

    public void setTaxWithholding(Double taxWithholding) {
        this.taxWithholding = taxWithholding;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaxPayer taxPayer = (TaxPayer) o;
        return id.equals(taxPayer.id) && annualTaxableIncome.equals(taxPayer.annualTaxableIncome) && taxWithholding.equals(taxPayer.taxWithholding);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, annualTaxableIncome, taxWithholding);
    }

    @Override
    public String toString() {
        return "TaxPayer: " +
                " | id = " + id +
                " | annualTaxableIncome = " + annualTaxableIncome +
                " | taxWithholding = " + taxWithholding ;
    }
}
