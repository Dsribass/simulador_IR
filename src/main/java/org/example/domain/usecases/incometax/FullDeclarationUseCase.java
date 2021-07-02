package org.example.domain.usecases.incometax;

import org.example.domain.entities.incometax.FullDeclaration;
import org.example.domain.entities.taxpayer.TaxPayer;
import org.example.domain.usecases.taxpayers.TaxPayerDAO;
import org.example.domain.usecases.utils.EntityNotExistsException;

import java.util.Optional;


public class FullDeclarationUseCase {
    private final TaxPayerDAO taxPayerDAO;

    public FullDeclarationUseCase(TaxPayerDAO taxPayerDAO) {
        this.taxPayerDAO = taxPayerDAO;
    }

    public Double simulateFullDeclaration(TaxPayer taxPayer) {
        if(taxPayer == null)
            throw new IllegalArgumentException("Tax Payer cannot be null");

        FullDeclaration fullDeclaration = new FullDeclaration();
        return fullDeclaration.simulateIncomeTaxDeclaration(taxPayer);
    }

    public Double simulateFullDeclaration(String idTaxPayer) {
        Optional<TaxPayer> optionalTaxPayer = taxPayerDAO.findOne(idTaxPayer);
        if(optionalTaxPayer.isEmpty())
            throw new EntityNotExistsException("Tax Payer was not created in database");

        return simulateFullDeclaration(optionalTaxPayer.get());
    }
}
