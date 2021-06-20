package org.example.domain.usecases.taxpayers;

import org.example.domain.entities.taxpayer.TaxPayer;
import org.example.domain.usecases.utils.ItemAlreadyExistsException;
import org.example.domain.usecases.utils.ItemNotExistsException;

import java.util.List;
import java.util.Optional;

public class TaxPayerUseCases {
    TaxPayerDAO taxPayerDAO;

    public TaxPayerUseCases(TaxPayerDAO taxPayerDAO) {
        this.taxPayerDAO = taxPayerDAO;
    }

    public Integer insert(TaxPayer taxPayer){
        if(taxPayer == null)
            throw new IllegalArgumentException("Tax Payer cannot be null");

        if(taxPayerDAO.findOne(taxPayer.getId()).isPresent())
            throw new ItemAlreadyExistsException("Tax payer is already created in database");

        return taxPayerDAO.insert(taxPayer);
    }

    public boolean update(Integer id, TaxPayer taxPayer) {
        if(id == null || taxPayer == null)
            throw new IllegalArgumentException("Tax Payer/ID cannot be null");

        if(taxPayerDAO.findOne(id).isEmpty())
            throw new ItemNotExistsException("Tax payer was not created in database");

        return taxPayerDAO.update(id,taxPayer);
    }

    public boolean deleteByKey(Integer id) {
        if(id == null) throw new IllegalArgumentException("ID Tax Payer cannot be null");

        if(taxPayerDAO.findOne(id).isEmpty())
            throw new ItemNotExistsException("Tax payer was not created in database");

        return taxPayerDAO.deleteByKey(id);

    }

    public boolean delete(TaxPayer taxPayer) {
        if(taxPayer == null) throw new IllegalArgumentException("ID Tax Payer cannot be null");

        if(taxPayerDAO.findOne(taxPayer.getId()).isEmpty())
            throw new ItemNotExistsException("Tax payer was not created in database");

        return taxPayerDAO.delete(taxPayer);
    }

    public List<TaxPayer> fetchAll(){
        return taxPayerDAO.fetchAll();
    }

    public Optional<TaxPayer> findOne(Integer id) {
        if(id == null) throw new IllegalArgumentException("ID Tax Payer cannot be null");

        return taxPayerDAO.findOne(id);
    }
}
