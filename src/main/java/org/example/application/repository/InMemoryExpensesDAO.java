package org.example.application.repository;
import org.example.domain.entities.expenses.Expense;
import org.example.domain.usecases.expenses.ExpensesDAO;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryExpensesDAO implements ExpensesDAO {
    public static Map<Integer, Expense> expensesTable = new LinkedHashMap<>();
    private static int id;

    @Override
    public List<Expense> fetchAllByTaxPayerId(String idTaxPayer) {
        return expensesTable.values().stream()
                .filter(expense -> expense.getTaxPayer().getId().equals(idTaxPayer))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Expense> findOne(Integer id) {
        if(expensesTable.containsKey(id))
            return Optional.of(expensesTable.get(id));
        return Optional.empty();
    }

    @Override
    public List<Expense> fetchAll() {
        return new ArrayList<>(expensesTable.values());
    }

    @Override
    public Integer insert(Expense expense) {
        expense.setId(++id);
        expensesTable.put(id,expense);
        return id;
    }

    @Override
    public boolean update(Integer key, Expense expense) {
        if(expensesTable.containsKey(id)){
            expensesTable.replace(id,expense);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Expense expense) {
        return deleteByKey(expense.getId());
    }

    @Override
    public boolean deleteByKey(Integer id) {
        if(expensesTable.containsKey(id)){
            expensesTable.remove(id);
            return true;
        }
        return false;
    }
}
