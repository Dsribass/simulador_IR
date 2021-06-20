package org.example.domain.entities.incometax;

import org.example.domain.entities.expenses.Expense;
import org.example.domain.entities.taxpayer.TaxPayer;

public class FullDeclaration extends IncomeTaxDeclaration{
    @Override
    public Double getDiscount(TaxPayer taxPayer) {
        return taxPayer.getExpenses().stream()
                .mapToDouble(Expense::getValueSpent).sum();
    }
}
