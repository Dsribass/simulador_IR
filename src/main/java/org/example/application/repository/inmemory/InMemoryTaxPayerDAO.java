package org.example.application.repository.inmemory;

import org.example.domain.entities.taxpayer.TaxPayer;
import org.example.domain.usecases.taxpayers.TaxPayerDAO;

import java.util.*;

public class InMemoryTaxPayerDAO implements TaxPayerDAO {
    public static Map<String, TaxPayer> taxPayerTable = new LinkedHashMap<>();

    @Override
    public Optional<TaxPayer> findOne(String id) {
        if(taxPayerTable.containsKey(id)) {
            return Optional.of(taxPayerTable.get(id));
        }
        return Optional.empty();
    }

    @Override
    public List<TaxPayer> fetchAll() {
        return new ArrayList<>(taxPayerTable.values());
    }

    @Override
    public String insert(TaxPayer taxPayer) {
        taxPayerTable.put(taxPayer.getId(), taxPayer);
        return taxPayer.getId();
    }

    @Override
    public boolean update(String id, TaxPayer taxPayer) {
        if(taxPayerTable.containsKey(id)){
            taxPayerTable.replace(id,taxPayer);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(TaxPayer taxPayer) {
        return deleteByKey(taxPayer.getId());
    }

    @Override
    public boolean deleteByKey(String id) {
        if(taxPayerTable.containsKey(id)){
            taxPayerTable.remove(id);
            return true;
        }
        return false;
    }
}
