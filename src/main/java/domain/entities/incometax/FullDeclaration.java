package domain.entities.incometax;

import domain.entities.expenses.Expense;
import domain.entities.taxpayer.TaxPayer;

public class FullDeclaration extends IncomeTaxDeclaration{
    @Override
    public Double getDiscount(TaxPayer taxPayer) {
        return taxPayer.getExpenses().stream()
                .mapToDouble(Expense::getValueSpent).sum();
    }
}
