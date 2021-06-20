package org.example.domain.entities.incometax;

import org.example.domain.entities.taxpayer.TaxPayer;

import java.util.ArrayList;
import java.util.List;

public abstract class IncomeTaxDeclaration {
    private final Double minimumLimitToSimulate = 22847.88;
    private final List<Taxes> taxes = new ArrayList<>();

    public IncomeTaxDeclaration() {
        double minimumLimitToPaySevenPercent = 22847.88;
        double minimumLimitToPayFifteenPercent = 33919.92;
        double minimumLimitToPayTwentyTwoPercent = 45012.72;
        double minimumLimitToPayTwentySevenPercent = 55976.16;
        double aliquotTwentySevenPercent = 0.275;
        double aliquotTwentyTwoPercent = 0.225;
        double aliquotFifteenPercent = 0.15;
        double aliquotSevenPercent = 0.075;
        taxes.add(new Taxes(aliquotTwentySevenPercent,minimumLimitToPayTwentySevenPercent));
        taxes.add(new Taxes(aliquotTwentyTwoPercent,minimumLimitToPayTwentyTwoPercent, minimumLimitToPayTwentySevenPercent));
        taxes.add(new Taxes(aliquotFifteenPercent,minimumLimitToPayFifteenPercent, minimumLimitToPayTwentyTwoPercent));
        taxes.add(new Taxes(aliquotSevenPercent,minimumLimitToPaySevenPercent, minimumLimitToPayFifteenPercent));
    }

    public final Double simulateIncomeTaxDeclaration(TaxPayer taxPayer){
        if(isBellowTheMinimumLimit(taxPayer.getAnnualTaxableIncome()))
            throw new NotExceededMinimumLimitException
                    ("The annual taxable income("+taxPayer.getAnnualTaxableIncome() +
                    ") is lower than the minimum limit: "+ minimumLimitToSimulate);

        Double discount = getDiscount(taxPayer);
        Double calculationBasis = taxPayer.getAnnualTaxableIncome() - discount;
        return calculateIncomeTax(calculationBasis,taxPayer.getTaxWithholding());
    }

    protected abstract Double getDiscount(TaxPayer taxPayer);

    private boolean isBellowTheMinimumLimit(Double amount){
        return amount < minimumLimitToSimulate;
    }

    private Double calculateIncomeTax(Double calculationBasis, Double taxPayed) {
        double incomeTax = taxes.stream()
                .filter(tax -> tax.isAboveTheLimit(calculationBasis))
                .mapToDouble(tax -> tax.getValueRangeTax(calculationBasis))
                .sum();
        return incomeTax - taxPayed;
    }
}
