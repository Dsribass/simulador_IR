package domain.entities.taxpayer;

import domain.entities.expenses.Expense;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TaxPayer {
    private Integer id;
    private Double annualTaxableIncome;
    private Double taxWithholding;
    private List<Expense> expenses = new ArrayList<>();

    public TaxPayer(Integer id,Double annualTaxableIncome, Double taxWithholding, List<Expense> expenses) {
        this.id = id;
        this.annualTaxableIncome = annualTaxableIncome;
        this.taxWithholding = taxWithholding;
        this.expenses = expenses;
    }

    public TaxPayer(Double annualTaxableIncome, Double taxWithholding) {
        this(null,annualTaxableIncome,taxWithholding,null);
    }

    public TaxPayer() {
    }

    public boolean addExpense(Expense expense) {
        if(!expenses.contains(expense))
            return this.expenses.add(expense);
        return false;
    }

    public boolean removeExpense(Expense expense){
        return this.expenses.remove(expense);
    }

    public List<Expense> getExpenses(){
        return expenses;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
