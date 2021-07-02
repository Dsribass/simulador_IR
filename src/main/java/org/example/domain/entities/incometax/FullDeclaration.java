package org.example.domain.entities.incometax;

import org.example.domain.entities.expenses.Expense;
import org.example.domain.entities.taxpayer.TaxPayer;

import java.util.List;

import static org.example.application.main.Main.expensesUseCases;

public class FullDeclaration extends IncomeTaxDeclaration{
    @Override
    public Double getDiscount(TaxPayer taxPayer) {
        List<Expense> expenses = expensesUseCases.fetchAllByTaxPayerId(taxPayer.getId());
        return expenses.stream()
                .mapToDouble(Expense::getValueSpent).sum();
    }
}
