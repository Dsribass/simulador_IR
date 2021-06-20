package org.example.domain.usecases.taxpayers;

import org.example.application.database.fakeDB;
import org.example.domain.entities.taxpayer.TaxPayer;
import org.example.domain.usecases.utils.DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaxPayerDAO implements DAO<TaxPayer,Integer> {
    private int idGenerate = 0;

    @Override
    public Optional<TaxPayer> findOne(Integer id) {
        if(fakeDB.db.containsKey(id))
            return Optional.of(fakeDB.db.get(id));
        return Optional.empty();
    }

    @Override
    public List<TaxPayer> fetchAll() {
        return new ArrayList<>(fakeDB.db.values());
    }

    @Override
    public Integer insert(TaxPayer taxPayer) {
        taxPayer.setId(++idGenerate);
        fakeDB.db.put(idGenerate,taxPayer);
        return idGenerate;
    }

    @Override
    public boolean update(Integer id, TaxPayer taxPayer) {
        if(fakeDB.db.containsKey(id)){
            fakeDB.db.replace(id,taxPayer);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(TaxPayer taxPayer) {
        return deleteByKey(taxPayer.getId());
    }

    @Override
    public boolean deleteByKey(Integer id) {
        if(fakeDB.db.containsKey(id)){
            fakeDB.db.remove(id);
            return true;
        }
        return false;
    }
}
