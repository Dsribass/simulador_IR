package org.example.domain.usecases.expenses;

import org.example.domain.entities.expenses.Expense;
import org.example.domain.usecases.utils.DAO;

import java.util.List;

public interface ExpensesDAO extends DAO<Expense, Integer> {
    List<Expense> fetchAllByTaxPayerId(String idTaxPayer);
}
