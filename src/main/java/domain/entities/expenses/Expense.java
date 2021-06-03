package domain.entities.expenses;

import java.util.Objects;

public class Expense {
    private ExpensesType type;
    private String name;
    private Double valueSpent;

    public Expense(ExpensesType type, String name, Double valueSpent) {
        this.type = type;
        this.name = name;
        this.valueSpent = valueSpent;
    }

    public Expense() {
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
