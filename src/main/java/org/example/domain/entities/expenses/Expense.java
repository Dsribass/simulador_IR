package org.example.domain.entities.expenses;

import org.example.domain.entities.taxpayer.TaxPayer;

import java.util.Objects;

public class Expense {
    private Integer id;
    private ExpensesType type;
    private String name;
    private Double valueSpent;

    TaxPayer taxPayer;

    public Expense(Integer id, ExpensesType type, String name, Double valueSpent, TaxPayer taxPayer) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.valueSpent = valueSpent;
        this.taxPayer = taxPayer;
    }

    public Expense(TaxPayer taxPayer) {
        this.taxPayer = taxPayer;
    }

    public Integer getId() {
        return id;
    }

    public TaxPayer getTaxPayer() {
        return taxPayer;
    }

    public String getTaxPayerId() {
        return taxPayer.getId();
    }

    public ExpensesType getType() {
        return type;
    }

    public void setType(ExpensesType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValueSpent() {
        return valueSpent;
    }

    public void setValueSpent(Double valueSpent) {
        this.valueSpent = valueSpent;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expense expenses = (Expense) o;
        return type == expenses.type && name.equals(expenses.name) && valueSpent.equals(expenses.valueSpent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, name, valueSpent);
    }

    @Override
    public String toString() {
        return "Expenses: " +
                " | type=" + type +
                " | name='" + name + '\'' +
                " | valueSpent=" + valueSpent;
    }
}
