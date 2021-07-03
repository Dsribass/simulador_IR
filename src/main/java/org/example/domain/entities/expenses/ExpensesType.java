package org.example.domain.entities.expenses;

import java.util.Arrays;

public enum ExpensesType {
    HEALTH("Saúde"),
    EDUCATION("Educação"),
    DEPENDENTS("Dependentes"),
    DONATIONS("Doações");

    private final String label;

    ExpensesType(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }

    public static ExpensesType toEnum(String enumValue) {
        return Arrays.stream(ExpensesType.values())
                .filter(type -> enumValue.equals(type.toString()))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}
