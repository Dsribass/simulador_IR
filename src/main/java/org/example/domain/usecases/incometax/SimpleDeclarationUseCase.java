package org.example.domain.usecases.incometax;

import org.example.domain.entities.incometax.SimpleDeclaration;
import org.example.domain.entities.taxpayer.TaxPayer;
import org.example.domain.usecases.taxpayers.TaxPayerDAO;
import org.example.domain.usecases.utils.EntityNotExistsException;

import java.util.Optional;


public class SimpleDeclarationUseCase {
    private final TaxPayerDAO taxPayerDAO;

    public SimpleDeclarationUseCase(TaxPayerDAO taxPayerDAO) {
        this.taxPayerDAO = taxPayerDAO;
    }

    public Double simulateSimpleDeclaration(TaxPayer taxPayer) {
        if(taxPayer == null)
            throw new IllegalArgumentException("Tax Payer cannot be null");

        SimpleDeclaration simpleDeclaration = new SimpleDeclaration();
        return simpleDeclaration.simulateIncomeTaxDeclaration(taxPayer);
    }

    public Double simulateSimpleDeclaration(String idTaxPayer) {
        Optional<TaxPayer> optionalTaxPayer = taxPayerDAO.findOne(idTaxPayer);
        if(optionalTaxPayer.isEmpty())
            throw new EntityNotExistsException("Tax Payer was not created in database");

        return simulateSimpleDeclaration(optionalTaxPayer.get());
    }
}
