package org.example.domain.entities.incometax;

import org.example.domain.entities.taxpayer.TaxPayer;

public class SimpleDeclaration extends IncomeTaxDeclaration{
    @Override
    public Double getDiscount(TaxPayer taxPayer) {
        double discountPercentage = 0.20;
        double discountLimit = 16754.34;
        double discount = taxPayer.getAnnualTaxableIncome() * discountPercentage;
        return Math.min(discount, discountLimit);
    }
}
