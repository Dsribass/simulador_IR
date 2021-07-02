package org.example.domain.usecases.taxpayers;

import org.example.domain.entities.taxpayer.TaxPayer;
import org.example.domain.usecases.utils.DAO;

import java.util.Optional;

public interface TaxPayerDAO extends DAO<TaxPayer,String> {
}
