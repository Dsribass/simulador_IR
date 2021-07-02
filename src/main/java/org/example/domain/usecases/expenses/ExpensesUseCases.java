package org.example.domain.usecases.expenses;

import org.example.domain.entities.expenses.Expense;
import org.example.domain.entities.taxpayer.TaxPayer;
import org.example.domain.usecases.taxpayers.TaxPayerDAO;
import org.example.domain.usecases.utils.DAO;
import org.example.domain.usecases.utils.EntityAlreadyExistsException;
import org.example.domain.usecases.utils.EntityNotExistsException;

import java.util.List;
import java.util.Optional;

public class ExpensesUseCases {
    private final ExpensesDAO expenseDAO;
    private final TaxPayerDAO taxPayerDAO;

    public ExpensesUseCases(ExpensesDAO expenseDAO, TaxPayerDAO taxPayerDAO) {
        this.expenseDAO = expenseDAO;
        this.taxPayerDAO = taxPayerDAO;
    }

    public Integer insert(Expense expense){
        if(expense == null)
            throw new IllegalArgumentException("Expense cannot be null");

        if(expense.getTaxPayer() == null)
            throw new IllegalArgumentException("The expense taxpayer cannot be null");

        if(taxPayerDAO.findOne(expense.getTaxPayerId()).isEmpty())
            throw new EntityNotExistsException("Tax Payer was not created in database");

        if(expenseDAO.findOne(expense.getId()).isPresent())
            throw new EntityAlreadyExistsException("Expense is already created in database");

        return expenseDAO.insert(expense);
    }

    public boolean update(Integer id, Expense expense) {
        if(id == null || expense == null)
            throw new IllegalArgumentException("Expense/ID cannot be null");

        if(expenseDAO.findOne(id).isEmpty())
            throw new EntityNotExistsException("Expense was not created in database");

        if(taxPayerDAO.findOne(expense.getTaxPayerId()).isEmpty())
            throw new EntityNotExistsException("Tax Payer was not created in database");

        return expenseDAO.update(id,expense);
    }

    public boolean deleteByKey(Integer id) {
        if(id == null) throw new IllegalArgumentException("ID Expense cannot be null");

        if(expenseDAO.findOne(id).isEmpty())
            throw new EntityNotExistsException("Expense was not created in database");

        return expenseDAO.deleteByKey(id);

    }

    public boolean delete(Expense expense) {
        if(expense == null) throw new IllegalArgumentException("ID Expense cannot be null");

        if(expenseDAO.findOne(expense.getId()).isEmpty())
            throw new EntityNotExistsException("Expense was not created in database");

        return expenseDAO.delete(expense);
    }

    public List<Expense> fetchAll(){
        return expenseDAO.fetchAll();
    }

    public List<Expense> fetchAllByTaxPayer(TaxPayer taxPayer){
        return fetchAllByTaxPayerId(taxPayer.getId());
    }

    public List<Expense> fetchAllByTaxPayerId(String idTaxPayer){
        if(idTaxPayer == null) throw new IllegalArgumentException("Id Tax Payer cannot be null");

        if(taxPayerDAO.findOne(idTaxPayer).isEmpty())
            throw new EntityNotExistsException("Tax payer was not created in database");

        return expenseDAO.fetchAllByTaxPayerId(idTaxPayer);
    }

    public Double getValueTotalExpenses(TaxPayer taxPayer){
        return fetchAllByTaxPayer(taxPayer).stream()
                .mapToDouble(Expense::getValueSpent).sum();
    }

    public Optional<Expense> findOne(Integer id) {
        if(id == null) throw new IllegalArgumentException("ID Expense cannot be null");

        return expenseDAO.findOne(id);
    }
}
